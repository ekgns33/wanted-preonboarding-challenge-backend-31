package org.ekgns33.commerce.product.service.dto.query.product;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroupResponse {
  private Long id;
  private String name;
  private Integer displayOrder;
  private List<ProductOptionResponse> options;

  @QueryProjection
  public ProductOptionGroupResponse(
      Long id, String name, Integer displayOrder, List<ProductOptionResponse> options) {
    this.id = id;
    this.name = name;
    this.displayOrder = displayOrder;
    this.options = options;
  }
}
