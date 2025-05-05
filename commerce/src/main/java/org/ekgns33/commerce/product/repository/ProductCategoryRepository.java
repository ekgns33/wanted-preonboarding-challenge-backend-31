package org.ekgns33.commerce.product.repository;

import java.util.List;
import org.ekgns33.commerce.product.domain.ProductCategory;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

  List<ProductCategory> findAllByProductId(Long id);
}
