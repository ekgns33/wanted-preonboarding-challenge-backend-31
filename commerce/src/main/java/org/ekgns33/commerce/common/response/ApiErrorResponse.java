package org.ekgns33.commerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.ekgns33.commerce.common.exception.CustomResponseCode;

@Getter
@JsonInclude(Include.NON_NULL)
public class ApiErrorResponse extends ApiResponse {

  private final ApiErrorDetail error;

  private ApiErrorResponse(ApiErrorDetail error) {
    super(false, null);
    this.error = error;
  }

  public static ApiErrorResponse of(final CustomResponseCode responseCode, final Object details) {
    return new ApiErrorResponse(
        ApiErrorDetail.of(responseCode.getCode(), responseCode.getMessage(), details));
  }
}
