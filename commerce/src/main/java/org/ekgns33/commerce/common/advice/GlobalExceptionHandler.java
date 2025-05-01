package org.ekgns33.commerce.common.advice;

import static org.ekgns33.commerce.common.exception.CommonErrorCode.CONFLICT;
import static org.ekgns33.commerce.common.exception.CommonErrorCode.INTERNAL_SERVER_ERROR;
import static org.ekgns33.commerce.common.exception.CommonErrorCode.INVALID_INPUT;
import static org.ekgns33.commerce.common.exception.CommonErrorCode.RESOURCE_NOT_FOUND;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ekgns33.commerce.common.exception.BusinessException;
import org.ekgns33.commerce.common.exception.ResourceConflictException;
import org.ekgns33.commerce.common.exception.ResourceNotFoundException;
import org.ekgns33.commerce.common.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e) {
    log.debug("Validation Error: {}", e.getMessage(), e);
    Map<String, Object> bindingResult = getBindingErrorDetail(e.getBindingResult());
    return ResponseEntity.badRequest().body(ApiErrorResponse.of(INVALID_INPUT, bindingResult));
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException e) {
    log.debug("Business Exception: {}", e.getMessage(), e);
    return ResponseEntity.status(e.getResponseCode().getHttpStatus())
        .body(ApiErrorResponse.of(e.getResponseCode(), e.getErrorDetail()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException e) {
    log.debug("Resource Not Found: {}", e.getMessage(), e);
    return ResponseEntity.status(RESOURCE_NOT_FOUND.getHttpStatus())
        .body(ApiErrorResponse.of(RESOURCE_NOT_FOUND, e.getErrorDetail()));
  }

  @ExceptionHandler(ResourceConflictException.class)
  protected ResponseEntity<ApiErrorResponse> handleResourceConflictException(
      ResourceConflictException e) {
    log.debug("Resource Conflict: {}", e.getMessage(), e);
    return ResponseEntity.status(CONFLICT.getHttpStatus())
        .body(ApiErrorResponse.of(CONFLICT, e.getErrorDetail()));
  }

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<ApiErrorResponse> handleException(Exception e) {
    log.error("Internal Error: {}", e.getMessage(), e);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR.getHttpStatus())
        .body(ApiErrorResponse.of(INTERNAL_SERVER_ERROR, null));
  }

  private Map<String, Object> getBindingErrorDetail(BindingResult bindingResult) {
    return bindingResult.getFieldErrors().stream()
        .collect(
            Collectors.toMap(
                FieldError::getField,
                error -> {
                  String msg = error.getDefaultMessage();
                  return msg != null ? msg : "올바르지 않은 입력입니다.";
                },
                (existing, replacement) -> existing));
  }
}
