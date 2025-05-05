package org.ekgns33.commerce.product.repository;

import java.util.List;
import org.ekgns33.commerce.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(
"""
select c from Category c
  join ProductCategory pc on pc.categoryId = c.id
  where pc.productId = :id
""")
  List<Category> findAllByProductId(Long id);
}
