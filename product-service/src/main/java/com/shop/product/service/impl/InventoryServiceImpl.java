package com.shop.product.service.impl;

import com.shop.product.dto.request.OrderItemRequest;
import com.shop.product.dto.request.OrderRequest;
import com.shop.product.enums.InventoryErrorCode;
import com.shop.product.repository.InventoryRepository;
import com.shop.product.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vuongdev.common.exception.AppException;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements IInventoryService {
  private final InventoryRepository inventoryRepository;

  @Override
  public void reduceInventory(OrderRequest orderItemRequests) {
    for (OrderItemRequest item : orderItemRequests.getOrderItemRequests()) {
      int updatedRows = inventoryRepository.reduceInventory(item.getProductId(), item.getQuantity());
      if (updatedRows == 0) {
        throw new AppException(InventoryErrorCode.PRODUCT_OUT_OF_STOCK);
      }
    }
  }
}
