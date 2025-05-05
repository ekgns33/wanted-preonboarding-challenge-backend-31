package org.ekgns33.commerce.product.repository;

import java.util.Optional;
import org.ekgns33.commerce.product.domain.ProductDetail;
import org.ekgns33.commerce.product.service.dto.query.product.ProductDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

  @Query(
      """
        select new org.ekgns33.commerce.product.service.dto.query.product.ProductDetailResponse(
          pd.weight,
          pd.dimensions,
          pd.materials,
          pd.countryOfOrigin,
          pd.warrantyInfo,
          pd.careInstructions,
          pd.additionalInfo
        )
        from ProductDetail pd
        where pd.productId = :id
    """)
  ProductDetailResponse findProductDetailDtoByProductId(Long id);

  Optional<ProductDetail> findByProductId(Long id);
}
