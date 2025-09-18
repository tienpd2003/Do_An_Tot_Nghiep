package com.shop.order.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
  UNPAID("UNPAID"),
  PAYING("PAYING"),
  PAID("PAID"),
  FAILED("FAILED"),
  REFUNDED("REFUNDED");

  private final String status;

  PaymentStatus(String status) {
    this.status = status;
  }
}
