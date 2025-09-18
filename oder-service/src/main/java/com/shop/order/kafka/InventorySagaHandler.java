package com.shop.order.kafka;

import com.shop.order.config.KafkaConfig;
import com.shop.order.constant.Message;
import com.shop.order.enums.OrderStatus;
import com.shop.order.dto.event.OrderRequestEvent;
import com.shop.order.dto.event.ProcessOrderFailedEvent;
import com.shop.order.dto.event.ReserveFailedEvent;
import com.shop.order.dto.request.OrderItemRequest;
import com.shop.order.entity.Order;
import com.shop.order.entity.OrderItem;
import com.shop.order.repository.InventoryRepository;
import com.shop.order.repository.OrderItemRepository;
import com.shop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.vuongdev.common.utils.ObjectMapperUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventorySagaHandler {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final InventoryRepository inventoryRepository;
  private final RedisTemplate<String, Object> redisTemplate;
  private final InventoryServiceImpl inventoryService;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  private String stockKey(Long productId) { return "stock:" + productId; }

  /**
   * Try to reserve all products atomically.
   * return true if all reserved (stocks decremented), false otherwise.
   */
  @KafkaListener(topics = KafkaConfig.ORDER_CMD_RESERVE_STOCK, groupId = "inventory-service")
  public void onReserveStock(String msg) {
    OrderRequestEvent data = ObjectMapperUtils.convertToObject(msg, OrderRequestEvent.class);
    reserve(data);
  }

  @KafkaListener(topics = KafkaConfig.ORDER_CMD_RESTORE_STOCK, groupId = "inventory-service")
  public void onRestoreStock(String msg) {
    List<OrderItemRequest> items = ObjectMapperUtils.convertToObjectList(msg, OrderItemRequest.class);
    restore(items);
  }

  @KafkaListener(topics = KafkaConfig.ORDER_CMD_PROCESS_ORDER, groupId = "inventory-service")
  @SneakyThrows
  public void onProcessOrder(String ticketId) {
    try {
      Order order = orderRepository.findByTicketId(ticketId).get();
      if (order.getStatus() == OrderStatus.FAILED) {
        log.warn("Ignoring message for failed order: {}", ticketId);
        return;
      }
      List<OrderItem> orderItems = orderItemRepository.findAllByOrderIs(order);
      List<OrderItem> orderItemUpdated = inventoryService.deductInventory(orderItems);

      // Logic lỗi nghiệp vụ: product out of stock
      if (orderItemUpdated.size() != orderItems.size()) {
        sendFailedProcessOrderEvent(ticketId, Message.PRODUCT_OUT_OF_STOCK);
        return;
      }

      // Logic thành công
      kafkaTemplate.send(KafkaConfig.ORDER_EVT_PROCESS_SUCCESS, ticketId);
    } catch (Exception e) {
      // Xử lý lỗi kỹ thuật: ném lỗi để Kafka retry
      log.error("Error when processing order {}", ticketId, e);
      throw e;
    }
  }

  private void sendFailedProcessOrderEvent(String ticketId, String reason) {
    ProcessOrderFailedEvent failedEvent = ProcessOrderFailedEvent.builder()
            .ticketId(ticketId)
            .reason(reason)
            .build();
    String msg = ObjectMapperUtils.convertToJson(failedEvent);
    kafkaTemplate.send(KafkaConfig.ORDER_EVT_PROCESS_FAILED, msg);
  }

  public void reserve(OrderRequestEvent data) {
    List<OrderItemRequest> items = data.getOrderItemRequests();
    if (items == null || items.isEmpty()) {
      //send message invalid order
      return;
    }

    for (OrderItemRequest item : items) {
      Object currentObj = redisTemplate.opsForValue().get(stockKey(item.getProductId()));
      Long current = currentObj == null ? null : Long.valueOf(currentObj.toString());
      if (current == null) {
        current = inventoryRepository.findById(item.getProductId()).get().getQuantity();
        redisTemplate.opsForValue().set(stockKey(item.getProductId()), current);
      }
      if (current < item.getQuantity()) {
        // send failure event
        ReserveFailedEvent failedEvent = ReserveFailedEvent.builder()
                .ticketId(data.getTicketId())
                .reason(Message.PRODUCT_OUT_OF_STOCK)
                .build();
        String msg = ObjectMapperUtils.convertToJson(failedEvent);
        kafkaTemplate.send(KafkaConfig.ORDER_EVT_RESERVE_FAILED, msg);
        return;
      }
    }

    String msg = ObjectMapperUtils.convertToJson(data);
    kafkaTemplate.send(KafkaConfig.ORDER_EVT_RESERVE_SUCCESS, msg);
  }

  /** Restore quantities (compensating) */
  public void restore(List<OrderItemRequest> items) {
    items.forEach(i -> redisTemplate.opsForValue().increment(stockKey(i.getProductId()), i.getQuantity()));
  }


}
