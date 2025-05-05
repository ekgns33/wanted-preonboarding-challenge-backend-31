package org.ekgns33.commerce.product.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ekgns33.commerce.common.exception.ResourceNotFoundException;
import org.ekgns33.commerce.common.response.ApiPageData;
import org.ekgns33.commerce.common.response.ApiPageInfo;
import org.ekgns33.commerce.product.domain.Product;
import org.ekgns33.commerce.product.repository.ProductCategoryRepository;
import org.ekgns33.commerce.product.repository.ProductDetailRepository;
import org.ekgns33.commerce.product.repository.ProductImageRepository;
import org.ekgns33.commerce.product.repository.ProductPriceRepository;
import org.ekgns33.commerce.product.repository.ProductTagRepository;
import org.ekgns33.commerce.product.repository.query.ReviewQueryRepositoryImpl;
import org.ekgns33.commerce.product.service.dto.query.product.ProductDetailQueryResponse;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductDetailResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductOptionGroupResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductRatingResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductTagResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductImageResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductPriceResponse;
import org.ekgns33.commerce.product.service.dto.query.ProductSimpleInfoDto;
import org.ekgns33.commerce.product.service.dto.query.product.ProductSearchQuery;
import org.ekgns33.commerce.product.repository.query.ProductSearchRepository;
import org.ekgns33.commerce.product.service.dto.SearchedProductDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQueryService {

  private final ProductSearchRepository productSearchRepository;
  private final ReviewQueryRepositoryImpl reviewQueryRepository;
  private final ProductViewAggregator productViewAggregator;

  @Transactional(readOnly = true)
  public ApiPageData<SearchedProductDto> searchProducts(ProductSearchQuery query) {
    Page<SearchedProductDto> fetchedPageable = productSearchRepository.search(query);
    return ApiPageData.of(
        fetchedPageable.getContent(),
        ApiPageInfo.of(
            fetchedPageable.getTotalElements(),
            fetchedPageable.getTotalPages(),
            query.pageable().getPageNumber(),
            query.pageable().getPageSize()));
  }

  @Transactional(readOnly = true)
  public ProductDetailQueryResponse getProductDetail(Long id) {

    ProductDetailQueryResponse productDetailView =
        productViewAggregator.aggregateProductDetailView(id);
    ProductRatingResponse productRatingResponse =
        reviewQueryRepository.findProductRatingWithReviewByProductId(id);
    productDetailView.setRating(productRatingResponse);
    productDetailView.setRelatedProducts(List.of());
    return productDetailView;
  }
}
