package org.ekgns33.commerce.common.exception;

import org.springframework.http.HttpStatus;

public interface CustomResponseCode {
  String getCode();

  String getDescription();

  HttpStatus getHttpStatus();

  String getMessage();
}
