package org.ekgns33.commerce.product.repository.query;

import java.util.List;
import java.util.Optional;
import org.ekgns33.commerce.product.service.dto.query.product.ProductOptionGroupResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductSearchQuery;
import org.ekgns33.commerce.product.service.dto.query.ProductSimpleInfoDto;
import org.ekgns33.commerce.product.service.dto.SearchedProductDto;
import org.springframework.data.domain.Page;

public interface ProductSearchRepository {
  Page<SearchedProductDto> search(ProductSearchQuery query);

  Optional<ProductSimpleInfoDto> findSimpleInfoById(Long id);

  List<ProductOptionGroupResponse> findProductOptionGroupDtoByProductId(Long id);
}
