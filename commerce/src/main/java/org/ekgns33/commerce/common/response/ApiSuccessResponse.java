package org.ekgns33.commerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccessResponse<T> extends ApiResponse {
  private final T data;

  protected ApiSuccessResponse(final T data) {
    super(true, "요청이 성공적으로 처리되었습니다.");
    this.data = data;
  }

  protected ApiSuccessResponse(final T data, final String message) {
    super(true, message);
    this.data = data;
  }

  public static <T> ApiSuccessResponse<T> of(final T payload, final String message) {
    return new ApiSuccessResponse<>(payload, message);
  }
}
