package org.ekgns33.commerce.common.exception;

import java.util.Map;
import lombok.Getter;

@Getter
public class ResourceConflictException extends RuntimeException {

  private final Map<String, Object> errorDetail;

  public ResourceConflictException(String message, Map<String, Object> errorDetail) {
    super(message);
    this.errorDetail = errorDetail;
  }

  public static ResourceConflictException of(String message, String fieldName, Object fieldValue) {
    return new ResourceConflictException(
        message,
        Map.of(
            "fieldName", fieldName,
            "fieldValue", fieldValue));
  }
}
