package org.ekgns33.commerce.product.service.dto.query.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTagResponse {
  private Long id;
  private String name;
  private String slug;

  public ProductTagResponse(Long id, String name, String slug) {
    this.id = id;
    this.name = name;
    this.slug = slug;
  }
}
