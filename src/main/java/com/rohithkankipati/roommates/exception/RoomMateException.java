package com.rohithkankipati.roommates.exception;

import org.springframework.http.HttpStatus;
import com.rohithkankipati.roommates.util.MessageUtil;

public class RoomMateException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final String errorCode;
  private final HttpStatus status;

  public RoomMateException(String errorCode, HttpStatus status) {
      super(MessageUtil.getMessage(errorCode));
      this.errorCode = errorCode;
      this.status = status;
  }

  public String getErrorCode() {
      return errorCode;
  }

  public HttpStatus getStatus() {
      return status;
  }
}
