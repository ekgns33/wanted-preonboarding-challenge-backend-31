package org.ekgns33.commerce.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ekgns33.commerce.common.exception.ResourceNotFoundException;
import org.ekgns33.commerce.product.domain.Brand;
import org.ekgns33.commerce.product.domain.Product;
import org.ekgns33.commerce.product.domain.ProductCategory;
import org.ekgns33.commerce.product.domain.ProductDetail;
import org.ekgns33.commerce.product.domain.ProductImage;
import org.ekgns33.commerce.product.domain.ProductOption;
import org.ekgns33.commerce.product.domain.ProductOptionGroup;
import org.ekgns33.commerce.product.domain.ProductPrice;
import org.ekgns33.commerce.product.domain.ProductTag;
import org.ekgns33.commerce.product.repository.BrandRepository;
import org.ekgns33.commerce.product.repository.ProductCategoryRepository;
import org.ekgns33.commerce.product.repository.ProductDetailRepository;
import org.ekgns33.commerce.product.repository.ProductImageRepository;
import org.ekgns33.commerce.product.repository.ProductOptionGroupRepository;
import org.ekgns33.commerce.product.repository.ProductOptionRepository;
import org.ekgns33.commerce.product.repository.ProductPriceRepository;
import org.ekgns33.commerce.product.repository.ProductRepository;
import org.ekgns33.commerce.product.repository.ProductTagRepository;
import org.ekgns33.commerce.product.service.dto.ProductMapper;
import org.ekgns33.commerce.product.service.dto.ProductSaveResponse;
import org.ekgns33.commerce.product.service.dto.command.DetailVO;
import org.ekgns33.commerce.product.service.dto.command.ImageVO;
import org.ekgns33.commerce.product.service.dto.command.OptionGroupVO;
import org.ekgns33.commerce.product.service.dto.command.OptionVO;
import org.ekgns33.commerce.product.service.dto.command.PriceVO;
import org.ekgns33.commerce.product.service.dto.command.ProductCategoryVO;
import org.ekgns33.commerce.product.service.dto.command.ProductCreateCommand;
import org.ekgns33.commerce.product.service.dto.command.ProductUpdateCommand;
import org.ekgns33.commerce.product.service.dto.command.ProductUpdateResponse;
import org.ekgns33.commerce.seller.domain.Seller;
import org.ekgns33.commerce.seller.repository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

  private final SellerRepository sellerRepository;
  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;
  private final ProductDetailRepository productDetailRepository;
  private final ProductPriceRepository productPriceRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final ProductOptionGroupRepository productOptionGroupRepository;
  private final ProductOptionRepository productOptionRepository;
  private final ProductImageRepository productImageRepository;
  private final ProductTagRepository productTagRepository;
  private final EntityUpdateHelper entityUpdateHelper;

  @Transactional
  public ProductSaveResponse createProduct(ProductCreateCommand command) {

    validateSellerAndBrand(command.sellerId(), command.brandId());

    Product product = ProductMapper.mapToProduct(command);
    productRepository.save(product);

    saveProductDetail(product.getId(), command.detailVO());
    saveProductPrice(product.getId(), command.priceVO());
    saveProductCategories(product.getId(), command.categories());
    saveProductOptionGroupsWithOptions(product.getId(), command.optionGroups());
    saveProductImages(product.getId(), command.imageVOS());
    saveProductTags(product.getId(), command.tags());

    return ProductSaveResponse.of(product);
  }

  @Transactional
  public ProductUpdateResponse updateProduct(Long id, ProductUpdateCommand command) {

    Product product = productRepository.findById(id)
        .orElseThrow(() -> ResourceNotFoundException.of(Product.class, id));
    validateSellerAndBrand(command.sellerId(), command.brandId());
    product.update(command);
    productRepository.save(product);

    ProductPrice productPrice = productPriceRepository.findByProductId(id)
        .orElseThrow(() -> ResourceNotFoundException.of(ProductPrice.class, id));
    productPrice.update(command.price());
    productPriceRepository.save(productPrice);

    ProductDetail productDetail = productDetailRepository.findByProductId(id)
        .orElseThrow(() -> ResourceNotFoundException.of(ProductDetail.class, id));
    productDetail.update(command.detail());
    productDetailRepository.save(productDetail);

    updateCategories(id, command.categories());

    List<ProductOptionGroup> productOptionGroups = productOptionGroupRepository.findAllByProductId(id);
    updateOptionGroups(id, productOptionGroups, command.optionGroups());

    updateProductImages(id, command.images());

    updateProductTags(id,command.tags());

    return ProductUpdateResponse.of(product);
  }

  private void updateCategories(Long productId, List<ProductCategoryVO> categories) {
    List<ProductCategory> productCategories = productCategoryRepository.findAllByProductId(productId);
    entityUpdateHelper
        .synchronizeCollection(
            productCategories,
            categories,
            ProductCategory::getCategoryId,
            ProductCategoryVO::categoryId,
            (vo) -> ProductCategory.withOutId(productId, vo.categoryId(), vo.isPrimary()),
            (entity, vo) -> entity.update(vo),
            productCategoryRepository::deleteAll,
            productCategoryRepository::saveAll
        );
  }

  private void updateProductImages(Long productId, List<ImageVO> imageVOS) {
    List<ProductImage> productImages = productImageRepository.findAllByProductId(productId);
    entityUpdateHelper
        .synchronizeCollection(
            productImages,
            imageVOS,
            ProductImage::getEntityKey,
            ImageVO::url,
            (vo) -> ProductImage.withOutId(productId, vo.optionId(), vo.url(), vo.altText(), vo.isPrimary(), vo.displayOrder()),
            (entity, vo) -> entity.update(vo),
            productImageRepository::deleteAll,
            productImageRepository::saveAll
        );
  }

  private void updateProductTags(Long productId, List<Long> tags) {
    List<ProductTag> productTags = productTagRepository.findAllByProductId(productId);
    entityUpdateHelper
        .synchronizeCollection(
            productTags,
            tags,
            ProductTag::getTagId,
            Function.identity(),
            (tagId) -> ProductMapper.mapToTag(productId, tagId),
            (entity, tagId) -> {},
            productTagRepository::deleteAll,
            productTagRepository::saveAll
        );
  }

  private void deleteRemovedImages(Map<String, ProductImage> imageMap, Map<String, ImageVO> imageVOMap) {
    List<ProductImage> toDelete = imageMap.values().stream()
        .filter(saved -> !imageVOMap.containsKey(saved.getUrl()))
        .toList();
    productImageRepository.deleteAll(toDelete);
  }

  private void upsertImages(Long productId, Map<String, ProductImage> imageMap, Map<String, ImageVO> imageVOMap) {
    imageVOMap.forEach(
        (url, imageVO) -> {
          ProductImage existing = imageMap.get(url);
          if (existing != null) {
            existing.update(imageVO);
          } else {
            ProductImage newImage = ProductImage.withOutId(productId, imageVO.optionId(), imageVO.url(), imageVO.altText(), imageVO.isPrimary(), imageVO.displayOrder());
            productImageRepository.save(newImage);
          }
        });
  }

  private void updateOptionGroups(Long id, List<ProductOptionGroup> productOptionGroups, List<OptionGroupVO> optionGroupVOS) {
    Map<String, ProductOptionGroup> optionGroupMap = productOptionGroups.stream()
        .collect(Collectors.toMap(ProductOptionGroup::getName, Function.identity()));

    Map<String, OptionGroupVO> optionGroupVOMap = optionGroupVOS.stream()
        .collect(Collectors.toMap(OptionGroupVO::name, Function.identity()));

    deleteRemovedOptionGroups(optionGroupMap, optionGroupVOMap);
    upsertOptionGroups(id, optionGroupMap, optionGroupVOMap);
  }

  private void upsertOptionGroups(Long productId, Map<String, ProductOptionGroup> optionGroupMap, Map<String, OptionGroupVO> optionGroupVOMap) {
    optionGroupVOMap.forEach(
        (name, optionGroupVO) -> {
          ProductOptionGroup existing = optionGroupMap.get(name);
          if (existing != null) {
            existing.update(optionGroupVO);
          } else {
            ProductOptionGroup newOptionGroup =
                ProductOptionGroup.withOutId(productId, name, optionGroupVO.displayOrder());
            productOptionGroupRepository.save(newOptionGroup);
            saveProductOptoin(newOptionGroup.getId(), optionGroupVO.optionVOS());
          }
        });
  }

  private void deleteRemovedOptionGroups(Map<String, ProductOptionGroup> optionGroupMap, Map<String, OptionGroupVO> optionGroupVOMap) {
    List<ProductOptionGroup> toDelete = optionGroupMap.values().stream()
        .filter(saved -> !optionGroupVOMap.containsKey(saved.getName()))
        .toList();
    Set<Long> optionGroupIdsToDelete = toDelete.stream()
        .map(ProductOptionGroup::getId)
        .collect(Collectors.toSet());
    List<ProductOption> optionsToDelete = productOptionRepository.findAllByOptionGroupIdIn(optionGroupIdsToDelete);
    productOptionRepository.deleteAll(optionsToDelete);
    productOptionGroupRepository.deleteAll(toDelete);
  }

  private void upsertCategories(Long productId, Map<Long, ProductCategory> savedMap, Map<Long, ProductCategoryVO> updatedMap) {
    updatedMap.forEach((categoryId, categoryVO) -> {
      ProductCategory existing = savedMap.get(categoryId);
      if (existing != null) {
        existing.update(categoryVO);
      } else {
        ProductCategory newCategory = ProductCategory.withOutId(
            productId,
            categoryVO.categoryId(),
            categoryVO.isPrimary()
        );
        productCategoryRepository.save(newCategory);
      }
    });
  }

  private void deleteRemovedCategories(Map<Long, ProductCategory> savedMap, Map<Long, ProductCategoryVO> updatedMap) {
    List<ProductCategory> toDelete = savedMap.values().stream()
        .filter(saved -> !updatedMap.containsKey(saved.getCategoryId()))
        .toList();

    productCategoryRepository.deleteAll(toDelete);
  }


  private void saveProductDetail(Long productId, DetailVO detailVOCommand) {
    ProductDetail productDetail = ProductMapper.mapToProductDetail(productId, detailVOCommand);
    productDetailRepository.save(productDetail);
  }

  private void saveProductPrice(Long productId, PriceVO priceVOCommand) {
    ProductPrice price = ProductMapper.mapToProductPrice(productId, priceVOCommand);
    productPriceRepository.save(price);
  }

  private void saveProductCategories(
      Long productId, List<ProductCategoryVO> productCategoryVOCommands) {
    if (productCategoryVOCommands != null && !productCategoryVOCommands.isEmpty()) {
      List<org.ekgns33.commerce.product.domain.ProductCategory> categories =
          productCategoryVOCommands.stream()
              .map(category -> ProductMapper.mapToCategory(productId, category))
              .toList();
      productCategoryRepository.saveAll(categories);
    }
  }

  private void saveProductOptionGroupsWithOptions(
      Long productId, List<OptionGroupVO> optionGroupVOCommands) {
    if (optionGroupVOCommands != null) {
      optionGroupVOCommands.forEach(
          optionGroupCommand -> {
            ProductOptionGroup productOptionGroup =
                ProductMapper.mapToOptionGroup(productId, optionGroupCommand);
            productOptionGroupRepository.save(productOptionGroup);

            if (optionGroupCommand.optionVOS() != null && !optionGroupCommand.optionVOS().isEmpty()) {
              saveProductOptoin(productOptionGroup.getId(), optionGroupCommand.optionVOS());
            }
          });
    }
  }

  private void saveProductOptoin(Long groupId, List<OptionVO> optionVOs) {
    List<ProductOption> options =
        optionVOs.stream()
            .map(option -> ProductMapper.mapToOption(groupId, option))
            .toList();
    productOptionRepository.saveAll(options);
  }

  private void saveProductImages(Long productId, List<ImageVO> imageVOCommands) {
    if (imageVOCommands != null && !imageVOCommands.isEmpty()) {
      List<ProductImage> images =
          imageVOCommands.stream().map(image -> ProductMapper.mapToImage(productId, image)).toList();
      productImageRepository.saveAll(images);
    }
  }

  private void saveProductTags(Long productId, List<Long> tagIds) {
    if (tagIds != null && !tagIds.isEmpty()) {
      List<ProductTag> tags =
          tagIds.stream()
              .map(
                  tagId ->
                      ProductMapper.mapToTag(
                          productId, tagId)) // 이 부분은 ProductTag가 tagId만 갖는 간단한 매핑 테이블이라 가정
              .toList();
      productTagRepository.saveAll(tags);
    }
  }

  private void validateSellerAndBrand(Long sellerId, Long brandId) {
    if (!sellerRepository.existsSellerById(sellerId)) {
      throw ResourceNotFoundException.of(Seller.class, sellerId);
    }
    if (!brandRepository.existsBrandById(brandId)) {
      throw ResourceNotFoundException.of(Brand.class, brandId);
    }
  }

}
