package com.shop.product.config;

public class KafkaConfig {
  public static final String ORDER_REQUESTS_TOPIC = "order-requests";
  public static final String ORDER_CMD_RESERVE_STOCK = "order-cmd-reserve-stock";
  public static final String ORDER_CMD_RESTORE_STOCK = "order-cmd-restore-stock";
  public static final String ORDER_CMD_PROCESS_PAYMENT  = "order-cmd-process-payment";
  public static final String ORDER_CMD_COMPENSATE_PAYMENT = "order-cmd-compensate-payment";
  public static final String ORDER_CMD_PROCESS_ORDER = "order-cmd-process-order";
  public static final String ORDER_EVT_RESERVE_SUCCESS = "order-evt-reserve-success";
  public static final String ORDER_EVT_RESERVE_FAILED = "order-evt-reserve-failed";
  public static final String PAYMENT_EVT_COMPLETED = "payment-evt-completed";
  public static final String PAYMENT_EVT_FAILED = "payment-evt-failed";
  public static final String ORDER_EVT_PROCESS_FAILED = "order-evt-process-failed";
  public static final String ORDER_EVT_PROCESS_SUCCESS = "order-evt-process-success";
}
