package com.shop.order.service;

import com.shop.order.entity.OrderItem;

import java.util.List;

public interface IInventoryService {
  List<OrderItem> deductInventory(List<OrderItem> orderItems);
  void restore(List<OrderItem> items);
}
