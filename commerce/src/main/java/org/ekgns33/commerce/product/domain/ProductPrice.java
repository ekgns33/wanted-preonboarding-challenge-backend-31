package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Table(name = "product_prices")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "base_price")
  private BigDecimal basePrice;

  @Column(name = "sale_price")
  private BigDecimal salePrice;

  @Column(name = "cost_price")
  private BigDecimal costPrice;

  @Enumerated(EnumType.STRING)
  @ColumnDefault(value = "KRW")
  private Currency currency;

  @Column(name = "tax_rate")
  private BigDecimal taxRate;

  @Builder
  private ProductPrice(
      Long id,
      Long productId,
      BigDecimal basePrice,
      BigDecimal salePrice,
      BigDecimal costPrice,
      Currency currency,
      BigDecimal taxRate) {
    this.id = id;
    this.productId = productId;
    this.basePrice = basePrice;
    this.salePrice = salePrice;
    this.costPrice = costPrice;
    this.currency = currency;
    this.taxRate = taxRate;
  }

  public static ProductPrice withOutId(
      Long productId,
      BigDecimal basePrice,
      BigDecimal salePrice,
      BigDecimal costPrice,
      Currency currency,
      BigDecimal taxRate) {
    return ProductPrice.builder()
        .productId(productId)
        .basePrice(basePrice)
        .salePrice(salePrice)
        .costPrice(costPrice)
        .currency(currency)
        .taxRate(taxRate)
        .build();
  }
}
