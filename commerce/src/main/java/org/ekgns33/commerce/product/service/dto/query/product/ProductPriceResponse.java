package org.ekgns33.commerce.product.service.dto.query.product;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ekgns33.commerce.product.domain.Currency;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPriceResponse {
  private BigDecimal basePrice;
  private BigDecimal salePrice;
  private BigDecimal costPrice;
  private Currency currency;
  private BigDecimal taxRate;

  public ProductPriceResponse(
      BigDecimal basePrice,
      BigDecimal salePrice,
      BigDecimal costPrice,
      Currency currency,
      BigDecimal taxRate) {
    this.basePrice = basePrice;
    this.salePrice = salePrice;
    this.costPrice = costPrice;
    this.currency = currency;
    this.taxRate = taxRate;
  }
}
