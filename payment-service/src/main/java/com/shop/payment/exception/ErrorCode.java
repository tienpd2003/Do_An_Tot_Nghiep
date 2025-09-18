package com.shop.payment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHENTICATED(401, "unauthenticated", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "access.denied", HttpStatus.FORBIDDEN),
    ERROR(500, "error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FORMAT(101, "invalid.format", HttpStatus.BAD_REQUEST),

    EMAIL_EXISTS(1000, "email.exists", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXISTS(1001, "username.not.exists", HttpStatus.UNAUTHORIZED),
    USERNAME_EXISTS(1002, "username.exists", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(1003, "login.failed", HttpStatus.UNAUTHORIZED),
    PRODUCT_NOT_FOUND(1004, "product.not.found", HttpStatus.NOT_FOUND),
    PRODUCT_OUT_OF_STOCK(1005, "product.out.of.stock", HttpStatus.BAD_REQUEST),;
    ;

    ErrorCode(Integer code, String messageKey, HttpStatusCode statusCode) {
        this.code = code;
        this.messageKey = messageKey;
        this.statusCode = statusCode;
    }
    private final Integer code;
    private final String messageKey;
    private final HttpStatusCode statusCode;
}