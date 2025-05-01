package org.ekgns33.commerce.common.exception;

import java.lang.reflect.Type;
import java.util.Map;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
  private final Map<String, Object> errorDetail;

  private ResourceNotFoundException(String message, Map<String, Object> errorDetail) {
    super(message);
    this.errorDetail = errorDetail;
  }

  public static ResourceNotFoundException of(Type resourceType, String resourceId) {
    return new ResourceNotFoundException(
        "요청한 리소스를 찾을 수 없습니다.",
        Map.of("resourceType", resourceType.getTypeName(), "resourceId", resourceId));
  }

  public static ResourceNotFoundException of(Type resourceType, Long resourceId) {
    return new ResourceNotFoundException(
        "요청한 리소스를 찾을 수 없습니다.",
        Map.of("resourceType", resourceType.getTypeName(), "resourceId", resourceId));
  }
}
