package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ekgns33.commerce.product.service.dto.command.ProductCategoryVO;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "product_categories")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "category_id")
  private Long categoryId;

  @Column(name = "is_primary")
  @ColumnDefault(value = "false")
  private Boolean isPrimary;

  @Builder
  private ProductCategory(Long id, Long productId, Long categoryId, Boolean isPrimary) {
    this.id = id;
    this.productId = productId;
    this.categoryId = categoryId;
    this.isPrimary = isPrimary;
  }

  public static ProductCategory withOutId(Long productId, Long categoryId, Boolean isPrimary) {
    return ProductCategory.builder()
        .productId(productId)
        .categoryId(categoryId)
        .isPrimary(isPrimary)
        .build();
  }

  public void update(ProductCategoryVO category) {
    this.categoryId = category.categoryId();
    this.isPrimary = category.isPrimary();
  }
}
