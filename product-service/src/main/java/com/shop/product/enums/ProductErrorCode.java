package com.shop.product.enums;

import com.shop.product.constants.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.vuongdev.common.exception.ErrorCode;

@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {
  PRODUCT_EXIST(1001, Message.PRODUCT_EXIST, HttpStatus.BAD_REQUEST),
  BRAND_NOT_FOUND(1002, Message.BRAND_NOT_FOUND, HttpStatus.NOT_FOUND),
  PRODUCT_NOT_FOUND(1003, Message.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND),
  CATEGORY_NOT_FOUND(1004, Message.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND),
  PRODUCT_VERSION_NOT_FOUND(1005, "product.version.not.found", HttpStatus.NOT_FOUND),
  PRODUCT_COLOR_NOT_FOUND(1006, "product.color.not.found", HttpStatus.NOT_FOUND),
  PRODUCT_ATTRIBUTE_NOT_FOUND(1007, "product.attribute.not.found", HttpStatus.NOT_FOUND)
  ;

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
