package org.ekgns33.commerce.product.repository;

import org.ekgns33.commerce.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
