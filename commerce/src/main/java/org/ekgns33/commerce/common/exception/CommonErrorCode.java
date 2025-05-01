package org.ekgns33.commerce.common.exception;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements CustomResponseCode {
  INVALID_INPUT("잘못된 입력데이터", HttpStatus.BAD_REQUEST, "입력 데이터가 유효하지 않습니다."),
  RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없음", HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
  UNAUTHORIZED("인증되지 않은 요청", HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
  FORBIDDEN("권한이 없는 요청", HttpStatus.FORBIDDEN, "해당 작업을 수행할 권한이 없습니다."),
  CONFLICT("리소스 충돌 발생", HttpStatus.CONFLICT, "리소스 충돌이 발생했습니다."),
  INTERNAL_SERVER_ERROR(
      "서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다. 잠시후 다시 시도해주세요.");

  private final String description;
  private final HttpStatus httpStatus;
  private final String message;

  CommonErrorCode(String description, HttpStatus httpStatus, String message) {
    this.description = description;
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
