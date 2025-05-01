package org.ekgns33.commerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDetail {

  private final String code;
  private final String message;
  private final Object details;

  private ApiErrorDetail(String code, String message, Object details) {
    this.code = code;
    this.message = message;
    this.details = details;
  }

  public static ApiErrorDetail of(final String code, final String message, final Object details) {
    return new ApiErrorDetail(code, message, details);
  }

  public static ApiErrorDetail messageOnly(final String code, final String message) {
    return new ApiErrorDetail(code, message, null);
  }
}
