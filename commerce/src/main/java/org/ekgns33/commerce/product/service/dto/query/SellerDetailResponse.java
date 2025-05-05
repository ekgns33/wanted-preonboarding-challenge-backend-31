package org.ekgns33.commerce.product.service.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerDetailResponse {
  private Long id;
  private String name;
  private String description;
  private String logoUrl;
  private BigDecimal rating;
  private String contactEmail;
  private String contactPhone;

  @QueryProjection
  public SellerDetailResponse(
      Long id,
      String name,
      String description,
      String logoUrl,
      BigDecimal rating,
      String contactEmail,
      String contactPhone) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.logoUrl = logoUrl;
    this.rating = rating;
    this.contactEmail = contactEmail;
    this.contactPhone = contactPhone;
  }
}
