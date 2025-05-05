package org.ekgns33.commerce.product.service.dto.query.product;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRatingResponse {

  public static final ProductRatingResponse EMPTY = new ProductRatingResponse();
  private BigDecimal average;
  private Long count;
  private Map<String, Object> distribution;

  @QueryProjection
  public ProductRatingResponse(BigDecimal average, Long count, Map<String, Object> distribution) {
    this.average = average;
    this.count = count;
    this.distribution = distribution;
  }
}
