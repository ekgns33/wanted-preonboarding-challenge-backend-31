package org.ekgns33.commerce.product.service.dto.query.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImageResponse {
  private Long id;
  private String url;
  private String altText;
  private boolean isPrimary;
  private Integer displayOrder;
  private Long optionId;

  public ProductImageResponse(
      Long id, String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {
    this.id = id;
    this.url = url;
    this.altText = altText;
    this.isPrimary = isPrimary;
    this.displayOrder = displayOrder;
    this.optionId = optionId;
  }
}
