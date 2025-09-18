package com.shop.product.service;

import com.shop.product.dto.request.OrderItemRequest;
import com.shop.product.dto.request.OrderRequest;

import java.util.List;

public interface IInventoryService {
  void reduceInventory(OrderRequest orderItemRequests);
}
