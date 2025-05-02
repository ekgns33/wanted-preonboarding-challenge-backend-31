package org.ekgns33.commerce.product.repository;

import org.ekgns33.commerce.product.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

  boolean existsSellerById(Long sellerId);
}
