package org.ekgns33.commerce.product.repository;

import java.util.List;
import org.ekgns33.commerce.product.domain.ProductImage;
import org.ekgns33.commerce.product.service.dto.query.product.ProductImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

  @Query(
      """
      select new org.ekgns33.commerce.product.service.dto.query.product.ProductImageResponse(
        pi.id,
        pi.url,
        pi.altText,
        pi.isPrimary,
        pi.displayOrder,
        pi.optionId
      )
      from ProductImage pi
      where pi.productId = :id
    """)
  List<ProductImageResponse> findProductImageDtoByProductId(Long id);

  List<ProductImage> findAllByProductId(Long id);
}
