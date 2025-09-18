package com.shop.order.enums;

import com.shop.order.constant.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.vuongdev.common.exception.ErrorCode;

@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  PRODUCT_NOT_FOUND(2001, Message.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND),
  ;

  private final Integer code;
  private final String message;
  private final HttpStatus statusCode;

  @Override
  public Integer getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public HttpStatus getStatusCode() {
    return this.statusCode;
  }
}
