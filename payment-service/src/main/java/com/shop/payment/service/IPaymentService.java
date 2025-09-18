package com.shop.payment.service;

public interface IPaymentService {
  boolean deductBalance(Long userId, Long amount);
  void refundBalance(Long userId, Long amount);
}
