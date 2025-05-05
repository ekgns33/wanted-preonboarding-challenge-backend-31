package org.ekgns33.commerce.product.service.dto.query.product;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ekgns33.commerce.product.domain.Currency;
import org.ekgns33.commerce.product.service.dto.SearchedProductDto.ImageDto;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRelatedResponse {
  private Long id;
  private String name;
  private String slug;
  private String shortDescription;
  private ImageDto primaryImage;
  private BigDecimal basePrice;
  private BigDecimal salePrice;
  private Currency currency;

  public ProductRelatedResponse(
      Long id,
      String name,
      String slug,
      String shortDescription,
      ImageDto primaryImage,
      BigDecimal basePrice,
      BigDecimal salePrice,
      Currency currency) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.shortDescription = shortDescription;
    this.primaryImage = primaryImage;
    this.basePrice = basePrice;
    this.salePrice = salePrice;
    this.currency = currency;
  }
}
