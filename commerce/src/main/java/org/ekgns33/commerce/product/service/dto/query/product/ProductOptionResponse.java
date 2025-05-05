package org.ekgns33.commerce.product.service.dto.query.product;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionResponse {
  private Long id;
  private String name;
  private BigDecimal additionalPrice;
  private String sku;
  private Integer stock;
  private Integer displayOrder;

  @QueryProjection
  public ProductOptionResponse(
      Long id,
      String name,
      BigDecimal additionalPrice,
      String sku,
      Integer stock,
      Integer displayOrder) {
    this.id = id;
    this.name = name;
    this.additionalPrice = additionalPrice;
    this.sku = sku;
    this.stock = stock;
    this.displayOrder = displayOrder;
  }
}
