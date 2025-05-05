package org.ekgns33.commerce.product.service.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandDetailResponse {
  private Long id;
  private String name;
  private String description;
  private String logoUrl;
  private String website;

  @QueryProjection
  public BrandDetailResponse(Long id, String name, String description, String logoUrl, String website) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.logoUrl = logoUrl;
    this.website = website;
  }
}
