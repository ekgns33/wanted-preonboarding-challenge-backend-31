package org.ekgns33.commerce.product.service.dto.query.product;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailResponse {
  private BigDecimal weight;
  private Map<String, Object> dimensions;
  private String materials;
  private String countryOfOrigin;
  private String warrantyInfo;
  private String careInstructions;
  private Map<String, Object> additionalInfo;

  public ProductDetailResponse(
      BigDecimal weight,
      Map<String, Object> dimensions,
      String materials,
      String countryOfOrigin,
      String warrantyInfo,
      String careInstructions,
      Map<String, Object> additionalInfo) {
    this.weight = weight;
    this.dimensions = dimensions;
    this.materials = materials;
    this.countryOfOrigin = countryOfOrigin;
    this.warrantyInfo = warrantyInfo;
    this.careInstructions = careInstructions;
    this.additionalInfo = additionalInfo;
  }
}
