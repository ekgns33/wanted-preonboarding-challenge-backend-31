package org.ekgns33.commerce.product.repository;

import org.ekgns33.commerce.product.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

  boolean existsBrandById(Long brandId);
}
