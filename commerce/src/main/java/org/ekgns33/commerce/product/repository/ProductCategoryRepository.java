package org.ekgns33.commerce.product.repository;

import java.util.List;
import org.ekgns33.commerce.product.domain.ProductCategory;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

  @Query(
      "SELECT new org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse("
          + "c.id, c.name, c.slug, pc.isPrimary, c.parent.id, c.parent.name, c.parent.slug) "
          + " FROM ProductCategory pc "
          + "JOIN fetch Category c ON pc.categoryId = c.id "
          + "LEFT JOIN Product p ON pc.productId = p.id "
          + "WHERE pc.productId = :id"
  )
  List<ProductCategoryResponse> findProductCategoryDtoByProductId(Long id);
}
