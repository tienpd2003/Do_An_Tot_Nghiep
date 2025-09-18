package com.shop.product.enums;

import com.shop.product.constants.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.vuongdev.common.exception.ErrorCode;

@AllArgsConstructor
public enum InventoryErrorCode implements ErrorCode {
  PRODUCT_OUT_OF_STOCK(3001, Message.PRODUCT_OUT_OF_STOCK, HttpStatus.BAD_REQUEST)
  ;

  private final Integer code;
  private final String message;
  private final HttpStatus status;

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
    return this.status;
  }
}
