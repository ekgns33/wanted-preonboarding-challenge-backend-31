package org.ekgns33.commerce.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ekgns33.commerce.common.response.ApiPageData;
import org.ekgns33.commerce.common.response.ApiPageInfo;
import org.ekgns33.commerce.product.service.dto.ProductSearchQuery;
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
}
