package com.shop.order.dto.event;

import com.shop.order.dto.request.OrderItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestEvent{
  private String ticketId;
  private Long userId;
  List<OrderItemRequest> orderItemRequests;
}
