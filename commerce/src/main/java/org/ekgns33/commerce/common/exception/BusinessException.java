package org.ekgns33.commerce.common.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

  private final CustomResponseCode responseCode;
  private final Map<String, Object> errorDetail;
}
