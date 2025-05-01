package org.ekgns33.commerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageData<T> {

  private List<T> items;
  private ApiPageInfo apiPageInfo;

  public static <T> ApiPageData<T> of(List<T> items, ApiPageInfo apiPageInfo) {
    return new ApiPageData<>(items, apiPageInfo);
  }
}
