package com.shop.order.dto.request;

import com.shop.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
  private Long userId;
  private List<OrderItemRequest> orderItemRequests;
}
