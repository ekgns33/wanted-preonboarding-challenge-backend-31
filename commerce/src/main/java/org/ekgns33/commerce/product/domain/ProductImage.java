package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "product_images")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "option_id")
  private Long optionId;

  @Column(nullable = false)
  private String url;

  @Column(name = "alt_text")
  private String altText;

  @Column(name = "is_primary")
  @ColumnDefault(value = "false")
  private Boolean isPrimary;

  @ColumnDefault(value = "0")
  private Integer displayOrder;

  public ProductImage(
      Long id,
      Long productId,
      Long optionId,
      String url,
      String altText,
      Boolean isPrimary,
      Integer displayOrder) {
    this.id = id;
    this.productId = productId;
    this.optionId = optionId;
    this.url = url;
    this.altText = altText;
    this.isPrimary = isPrimary;
    this.displayOrder = displayOrder;
  }

  public static ProductImage withOutId(
      Long productId,
      Long optionId,
      String url,
      String altText,
      Boolean isPrimary,
      Integer displayOrder) {
    return new ProductImage(null, productId, optionId, url, altText, isPrimary, displayOrder);
  }
}
