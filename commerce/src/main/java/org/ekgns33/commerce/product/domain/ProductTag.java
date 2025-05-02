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

@Table(name = "product_tags")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "tag_id")
  private Long tagId;

  @Builder
  private ProductTag(Long id, Long productId, Long tagId) {
    this.id = id;
    this.productId = productId;
    this.tagId = tagId;
  }

  public static ProductTag withOutId(Long productId, Long tagId) {
    return ProductTag.builder().productId(productId).tagId(tagId).build();
  }
}
