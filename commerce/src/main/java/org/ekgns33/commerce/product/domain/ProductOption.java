package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "product_options")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long optionGroupId;

  @Column(nullable = false)
  private String name;

  @ColumnDefault(value = "0")
  private BigDecimal additionalPrice;

  @Column(name = "sku")
  private String sku;

  @ColumnDefault(value = "0")
  private Integer stock;

  @ColumnDefault(value = "0")
  private Integer displayOrder;

  @Builder
  private ProductOption(
      Long id,
      Long optionGroupId,
      String name,
      BigDecimal additionalPrice,
      String sku,
      Integer stock,
      Integer displayOrder) {
    this.id = id;
    this.optionGroupId = optionGroupId;
    this.name = name;
    this.additionalPrice = additionalPrice;
    this.sku = sku;
    this.stock = stock;
    this.displayOrder = displayOrder;
  }

  public static ProductOption withOutId(
      Long optionGroupId,
      String name,
      BigDecimal additionalPrice,
      String sku,
      Integer stock,
      Integer displayOrder) {
    return ProductOption.builder()
        .optionGroupId(optionGroupId)
        .name(name)
        .additionalPrice(additionalPrice)
        .sku(sku)
        .stock(stock)
        .displayOrder(displayOrder)
        .build();
  }
}
