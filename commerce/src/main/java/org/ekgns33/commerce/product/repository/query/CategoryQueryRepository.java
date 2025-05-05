package org.ekgns33.commerce.product.repository.query;

import java.util.List;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;

public interface CategoryQueryRepository {

  List<ProductCategoryResponse> findProductCategoryDtoByProductId(Long id);
}
