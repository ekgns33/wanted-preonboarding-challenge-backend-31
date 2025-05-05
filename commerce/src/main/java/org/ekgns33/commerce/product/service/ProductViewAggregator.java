package org.ekgns33.commerce.product.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ekgns33.commerce.common.exception.ResourceNotFoundException;
import org.ekgns33.commerce.product.domain.Category;
import org.ekgns33.commerce.product.repository.CategoryRepository;
import org.ekgns33.commerce.product.domain.Product;
import org.ekgns33.commerce.product.repository.ProductCategoryRepository;
import org.ekgns33.commerce.product.repository.ProductDetailRepository;
import org.ekgns33.commerce.product.repository.ProductImageRepository;
import org.ekgns33.commerce.product.repository.ProductPriceRepository;
import org.ekgns33.commerce.product.repository.ProductTagRepository;
import org.ekgns33.commerce.product.repository.query.CategoryQueryRepositoryImpl;
import org.ekgns33.commerce.product.repository.query.ProductSearchRepository;
import org.ekgns33.commerce.product.service.dto.query.ProductSimpleInfoDto;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductDetailQueryResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductDetailResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductImageResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductOptionGroupResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductPriceResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductRatingResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductTagResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductViewAggregator {

  private final CategoryRepository categoryRepository;

  private final ProductSearchRepository productSearchRepository;
  private final ProductDetailRepository productDetailRepository;
  private final ProductPriceRepository productPriceRepository;
  private final ProductImageRepository productImageRepository;
  private final ProductTagRepository productTagRepository;
  private final CategoryQueryRepositoryImpl categoryQueryRepository;

  public ProductDetailQueryResponse aggregateProductDetailView(Long id) {
    ProductSimpleInfoDto simpleInfoDto =
        productSearchRepository
            .findSimpleInfoById(id)
            .orElseThrow(
                () -> ResourceNotFoundException.of(Product.class, id, "요청한 상품을 찾을 수 없습니다."));
    ProductDetailResponse productDetailResponse =
        productDetailRepository.findProductDetailDtoByProductId(id);
    ProductPriceResponse productPriceResponse =
        productPriceRepository.findProductPriceDtoByProductId(id);
    List<ProductCategoryResponse> productCategoryDtos =
        categoryQueryRepository.findProductCategoryDtoByProductId(id);
    List<ProductOptionGroupResponse> productOptionGroupResponses =
        productSearchRepository.findProductOptionGroupDtoByProductId(id);
    List<ProductImageResponse> productImageResponses =
        productImageRepository.findProductImageDtoByProductId(id);
    List<ProductTagResponse> productTagResponses =
        productTagRepository.findProductTagDtoByProductId(id);

    return new ProductDetailQueryResponse(
        simpleInfoDto,
        productDetailResponse,
        productPriceResponse,
        productCategoryDtos,
        productOptionGroupResponses,
        productImageResponses,
        productTagResponses,
        ProductRatingResponse.EMPTY,
        null);
  }
}
