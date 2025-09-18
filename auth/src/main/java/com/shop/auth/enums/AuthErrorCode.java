package com.shop.auth.enums;

import com.shop.auth.constant.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.vuongdev.common.exception.ErrorCode;

@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
  USERNAME_EXISTS(1001, Message.USERNAME_EXISTS, HttpStatus.BAD_REQUEST),
  USERNAME_NOT_EXISTS(1002, Message.USERNAME_NOT_EXISTS, HttpStatus.BAD_REQUEST),
  LOGIN_FAILED(1003, Message.LOGIN_FAILED, HttpStatus.BAD_REQUEST),
  EMAIL_EXISTS(1004, Message.EMAIL_EXISTS, HttpStatus.BAD_REQUEST),;

  private final Integer code;
  private final String message;
  private final HttpStatus statusCode;

  @Override
  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
