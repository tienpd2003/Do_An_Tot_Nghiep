package com.shop.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestMessage {
  private String ticketId;
  private Long userId;
  List<OrderItemRequest> orderItemRequests;
}
