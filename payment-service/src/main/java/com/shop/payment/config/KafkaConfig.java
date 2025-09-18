package com.shop.payment.config;

public class KafkaConfig {
  public static final String ORDER_CMD_PROCESS_PAYMENT  = "order-cmd-process-payment";
  public static final String PAYMENT_EVT_COMPLETED = "payment-evt-completed";
  public static final String PAYMENT_EVT_FAILED = "payment-evt-failed";
  public static final String ORDER_CMD_COMPENSATE_PAYMENT = "order-cmd-compensate-payment";
}