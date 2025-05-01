package org.ekgns33.commerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageResponse<T> extends ApiSuccessResponse<ApiPageData<T>> {

  private ApiPageData<T> data;

  private ApiPageResponse(final ApiPageData<T> data) {
    super(data);
  }

  public static <T> ApiPageResponse<T> of(final ApiPageData<T> data) {
    return new ApiPageResponse<>(data);
  }
}
