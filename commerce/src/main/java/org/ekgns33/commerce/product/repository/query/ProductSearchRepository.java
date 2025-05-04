package org.ekgns33.commerce.product.repository.query;

import org.ekgns33.commerce.product.service.dto.ProductSearchQuery;
import org.ekgns33.commerce.product.service.dto.SearchedProductDto;
import org.springframework.data.domain.Page;

public interface ProductSearchRepository {
  Page<SearchedProductDto> search(ProductSearchQuery query);
}
