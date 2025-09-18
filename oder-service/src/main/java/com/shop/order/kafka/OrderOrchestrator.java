package com.shop.order.kafka;

import com.shop.order.config.KafkaConfig;
import com.shop.order.enums.OrderStatus;
import com.shop.order.enums.PaymentStatus;
import com.shop.order.dto.event.*;
import com.shop.order.dto.request.OrderItemRequest;
import com.shop.order.entity.Order;
import com.shop.order.entity.OrderItem;
import com.shop.order.entity.Product;
import com.shop.order.enums.OrderErrorCode;
import com.shop.order.repository.OrderItemRepository;
import com.shop.order.repository.OrderRepository;
import com.shop.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.vuongdev.common.exception.AppException;
import org.vuongdev.common.utils.ObjectMapperUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderOrchestrator {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final StringRedisTemplate statusOrderCache, paymentStatusCache;
  private final KafkaTemplate<String, String> kafkaTemplate;

  private String statusKey(String ticketId) {
    return "order:status:" + ticketId;
  }

  private String paymentStatusKey(String ticketId) {
    return "payment:status:" + ticketId;
  }

  public String startOrder(Long userId, List<OrderItemRequest> items) {

    //Make sure user exists befoore

    List<Long> productIds = items.stream().map(OrderItemRequest::getProductId).toList();
    List<Product> products = productRepository.findAllById(productIds);
    if (products.size() != productIds.size()) {
      throw new AppException(OrderErrorCode.PRODUCT_NOT_FOUND);
    }

    Map<Long, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductId, p -> p));
    Long total = (long) items.stream()
            .mapToDouble(i -> productMap.get(i.getProductId()).getPrice() * i.getQuantity())
            .sum();
    String ticketId = UUID.randomUUID().toString();

    Order order = orderRepository.save(
            Order.builder()
                    .userId(userId)
                    .ticketId(ticketId)
                    .orderCode("ORD-" + ticketId)
                    .status(OrderStatus.CREATED)
                    .totalAmount(total)
                    .build()
    );

    List<OrderItem> orderItems = items.stream()
            .map(item -> {
              Product product = productMap.get(item.getProductId());
              return OrderItem.builder()
                      .order(order)
                      .product(product)
                      .quantity(item.getQuantity())
                      .build();
            })
            .collect(Collectors.toList());
    orderItemRepository.saveAll(orderItems);

    OrderRequestEvent reserveEvent = OrderRequestEvent.builder()
            .ticketId(ticketId)
            .userId(userId)
            .orderItemRequests(items)
            .build();

    statusOrderCache.opsForValue().set(statusKey(ticketId), OrderStatus.CREATED.getStatus());
    paymentStatusCache.opsForValue().set(paymentStatusKey(ticketId), PaymentStatus.UNPAID.getStatus());
    String msg = ObjectMapperUtils.convertToJson(reserveEvent);
    kafkaTemplate.send(KafkaConfig.ORDER_CMD_RESERVE_STOCK, msg);

    // Step 1: Reserve stock
    // sendReserveStockCommand(orderId);

    // Step 2: Process payment
    // sendProcessPaymentCommand(orderId);

    // Step 3: Confirm order
    // confirmOrder(orderId);

    return "ORD-" + ticketId;
  }

  // Nhận sự kiện giữ hàng thành công
  @KafkaListener(topics = KafkaConfig.ORDER_EVT_RESERVE_SUCCESS, groupId = "order-service")
  @SneakyThrows
  public void onReserveSuccess(String msg) {
    OrderRequestEvent evt = ObjectMapperUtils.convertToObject(msg, OrderRequestEvent.class);
    String statusOrder = statusOrderCache.opsForValue().get(statusKey(evt.getTicketId()));
    if (statusOrder == null || !statusOrder.equals(OrderStatus.CREATED.getStatus())) {
      orderRepository.findByTicketId(evt.getTicketId()).ifPresent(o -> {
        o.setStatus(OrderStatus.FAILED);
        orderRepository.save(o);
      });

      paymentStatusCache.delete(paymentStatusKey(evt.getTicketId()));
      statusOrderCache.delete(statusKey(evt.getTicketId()));
      //send event to web socket
      return;
    }
    statusOrderCache.opsForValue().set(statusKey(evt.getTicketId()), OrderStatus.CONFIRMED.getStatus());
    paymentStatusCache.opsForValue().set(paymentStatusKey(evt.getTicketId()), PaymentStatus.PAYING.getStatus());
    ProcessPaymentRequest payCmd = ProcessPaymentRequest.builder()
            .ticketId(evt.getTicketId())
            .userId(evt.getUserId())
            .amount(orderRepository.findByTicketId(evt.getTicketId()).get().getTotalAmount())
            .build();
    kafkaTemplate.send(KafkaConfig.ORDER_CMD_PROCESS_PAYMENT, ObjectMapperUtils.convertToJson(payCmd));
  }


  // Nhận sự kiện giữ hàng thất bại
  @KafkaListener(topics = KafkaConfig.ORDER_EVT_RESERVE_FAILED, groupId = "order-service")
  @SneakyThrows
  public void onReserveFail(String msg) {
    ReserveFailedEvent evt = ObjectMapperUtils.convertToObject(msg, ReserveFailedEvent.class);
    assert evt != null;
    orderRepository.findByTicketId(evt.getTicketId()).ifPresent(o -> {
      o.setStatus(OrderStatus.FAILED);
      orderRepository.save(o);
    });
    paymentStatusCache.delete(paymentStatusKey(evt.getTicketId()));
    statusOrderCache.delete(statusKey(evt.getTicketId()));
    //send event to web socket
  }

  // Nhận thanh toán thành công
  @KafkaListener(topics = KafkaConfig.PAYMENT_EVT_COMPLETED, groupId = "order-service")
  @SneakyThrows
  public void onPaymentCompleted(String msg) {
    PaymentEvent evt = ObjectMapperUtils.convertToObject(msg, PaymentEvent.class);
    assert evt != null;
    Optional<Order> o = orderRepository.findByTicketId(evt.getTicketId());
    if (o.isEmpty()) {
      // send event to web socket
      return;
    }
    paymentStatusCache.opsForValue().set(paymentStatusKey(evt.getTicketId()), PaymentStatus.PAID.getStatus());
    statusOrderCache.opsForValue().set(statusKey(evt.getTicketId()), OrderStatus.PROCESSING.getStatus());
    kafkaTemplate.send(KafkaConfig.ORDER_CMD_PROCESS_ORDER, evt.getTicketId());
  }

  // Nhận thanh toán thất bại → bù trừ: release stock + reject
  @KafkaListener(topics = KafkaConfig.PAYMENT_EVT_FAILED, groupId = "order-service")
  @SneakyThrows
  public void onPaymentFailed(String msg) {
    PaymentEvent evt = ObjectMapperUtils.convertToObject(msg, PaymentEvent.class);
    Optional<Order> o = orderRepository.findByTicketId(evt.getTicketId());
    if (o.isEmpty()) {
      // send event to web socket
      return;
    }
    Order order = o.get();
    order.setStatus(OrderStatus.FAILED);
    orderRepository.save(order);
    paymentStatusCache.delete(paymentStatusKey(evt.getTicketId()));
    statusOrderCache.delete(statusKey(evt.getTicketId()));
    // send event to web socket
  }

  @KafkaListener(topics = KafkaConfig.ORDER_EVT_PROCESS_FAILED, groupId = "order-service")
  @SneakyThrows
  public void onProcessOrderFailed(String msg) {
    ProcessOrderFailedEvent evt = ObjectMapperUtils.convertToObject(msg, ProcessOrderFailedEvent.class);
    if (evt == null) {
      log.error("Invalid event: {}", msg);
      return;
    }

    String ticketId = evt.getTicketId();
    Optional<Order> o = orderRepository.findByTicketId(ticketId);
    if (o.isEmpty()) {
      // notify websocket
      return;
    }
    paymentStatusCache.delete(paymentStatusKey(ticketId));
    statusOrderCache.delete(statusKey(ticketId));

    Order order = o.get();
    order.setStatus(OrderStatus.FAILED);
    orderRepository.save(order);

    CompensatePaymentEvent compensatePaymentEvent = CompensatePaymentEvent.builder()
            .userId(order.getUserId())
            .amount(order.getTotalAmount())
            .build();

    String cmd = ObjectMapperUtils.convertToJson(compensatePaymentEvent);
    kafkaTemplate.send(KafkaConfig.ORDER_CMD_COMPENSATE_PAYMENT, cmd);
  }

  @KafkaListener(topics = KafkaConfig.ORDER_EVT_PROCESS_SUCCESS, groupId = "order-service")
  @SneakyThrows
  public void onProcessOrderSuccess(String ticketId) {
    Optional<Order> o = orderRepository.findByTicketId(ticketId);
    if (o.isEmpty()) {
      // send event to web socket
      return;
    }
    Order order = o.get();
    order.setStatus(OrderStatus.PROCESSING);
    orderRepository.save(order);
    paymentStatusCache.delete(paymentStatusKey(ticketId));
    statusOrderCache.delete(statusKey(ticketId));
    // send event to web socket
  }
}
