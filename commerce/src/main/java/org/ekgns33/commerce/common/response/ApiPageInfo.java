package org.ekgns33.commerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageInfo {
  private Long totalItems;
  private Long totalPages;
  private Long currentPage;
  private Long perPage;

  public static ApiPageInfo of(long totalItems, long totalPages, Long currentPage, Long perPage) {
    return new ApiPageInfo(totalItems, totalPages, currentPage, perPage);
  }
}
