package org.ekgns33.commerce.product.repository;

import org.ekgns33.commerce.product.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {}
