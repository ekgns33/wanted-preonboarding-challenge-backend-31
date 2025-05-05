package org.ekgns33.commerce.product.service;

import java.util.List;
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
import org.ekgns33.commerce.seller.domain.Seller;
import org.ekgns33.commerce.product.repository.BrandRepository;
import org.ekgns33.commerce.product.repository.ProductCategoryRepository;
import org.ekgns33.commerce.product.repository.ProductDetailRepository;
import org.ekgns33.commerce.product.repository.ProductImageRepository;
import org.ekgns33.commerce.product.repository.ProductOptionGroupRepository;
import org.ekgns33.commerce.product.repository.ProductOptionRepository;
import org.ekgns33.commerce.product.repository.ProductPriceRepository;
import org.ekgns33.commerce.product.repository.ProductRepository;
import org.ekgns33.commerce.product.repository.ProductTagRepository;
import org.ekgns33.commerce.seller.repository.SellerRepository;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.OptionGroup;
import org.ekgns33.commerce.product.service.dto.ProductMapper;
import org.ekgns33.commerce.product.service.dto.ProductSaveResponse;
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

  @Transactional
  public ProductSaveResponse createProduct(ProductCreateCommand command) {

    validateSellerAndBrand(command.sellerId(), command.brandId());

    Product product = ProductMapper.mapToProduct(command);
    productRepository.save(product);

    saveProductDetail(product.getId(), command.detail());
    saveProductPrice(product.getId(), command.price());
    saveProductCategories(product.getId(), command.categories());
    saveProductOptionGroupsWithOptions(product.getId(), command.optionGroups());
    saveProductImages(product.getId(), command.images());
    saveProductTags(product.getId(), command.tags());

    return ProductSaveResponse.of(product);
  }

  private void saveProductDetail(Long productId, ProductCreateCommand.Detail detailCommand) {
    ProductDetail productDetail = ProductMapper.mapToProductDetail(productId, detailCommand);
    productDetailRepository.save(productDetail);
  }

  private void saveProductPrice(Long productId, ProductCreateCommand.Price priceCommand) {
    ProductPrice price = ProductMapper.mapToProductPrice(productId, priceCommand);
    productPriceRepository.save(price);
  }

  private void saveProductCategories(
      Long productId, List<ProductCreateCommand.Category> categoryCommands) {
    if (categoryCommands != null && !categoryCommands.isEmpty()) {
      List<ProductCategory> categories =
          categoryCommands.stream()
              .map(category -> ProductMapper.mapToCategory(productId, category))
              .toList();
      productCategoryRepository.saveAll(categories);
    }
  }

  private void saveProductOptionGroupsWithOptions(
      Long productId, List<OptionGroup> optionGroupCommands) {
    if (optionGroupCommands != null) {
      optionGroupCommands.forEach(
          optionGroupCommand -> {
            // Save Option Group
            ProductOptionGroup productOptionGroup =
                ProductMapper.mapToOptionGroup(productId, optionGroupCommand);
            productOptionGroupRepository.save(productOptionGroup);

            // Save Options within the group
            if (optionGroupCommand.options() != null && !optionGroupCommand.options().isEmpty()) {
              List<ProductOption> options =
                  optionGroupCommand.options().stream()
                      .map(option -> ProductMapper.mapToOption(productOptionGroup.getId(), option))
                      .toList();
              productOptionRepository.saveAll(options);
            }
          });
    }
  }

  private void saveProductImages(Long productId, List<ProductCreateCommand.Image> imageCommands) {
    if (imageCommands != null && !imageCommands.isEmpty()) {
      List<ProductImage> images =
          imageCommands.stream().map(image -> ProductMapper.mapToImage(productId, image)).toList();
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
