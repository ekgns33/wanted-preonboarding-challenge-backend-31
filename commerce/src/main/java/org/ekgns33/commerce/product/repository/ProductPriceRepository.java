package org.ekgns33.commerce.product.repository;

import org.ekgns33.commerce.product.domain.ProductPrice;
import org.ekgns33.commerce.product.service.dto.query.product.ProductPriceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

  @Query(
      """
              select new org.ekgns33.commerce.product.service.dto.query.product.ProductPriceResponse(
                pp.basePrice,
                pp.salePrice,
                pp.costPrice,
                pp.currency,
                pp.taxRate
              )
              from ProductPrice pp
              where pp.productId = :id
    """)
  ProductPriceResponse findProductPriceDtoByProductId(Long id);
}
