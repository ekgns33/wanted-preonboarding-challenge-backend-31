package org.ekgns33.commerce.seller.repository;

import org.ekgns33.commerce.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

  boolean existsSellerById(Long sellerId);
}
