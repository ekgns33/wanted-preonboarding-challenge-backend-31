package org.ekgns33.commerce.product.repository;

import java.util.List;
import org.ekgns33.commerce.product.domain.ProductTag;
import org.ekgns33.commerce.product.service.dto.query.product.ProductTagResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

  @Query(
"""
      select new org.ekgns33.commerce.product.service.dto.query.product.ProductTagResponse(
        t.id,
        t.slug,
        t.slug
      )
      from ProductTag pt
      join Tag t on pt.tagId = t.id
      where pt.productId = :id
    """)
  List<ProductTagResponse> findProductTagDtoByProductId(Long id);
}
