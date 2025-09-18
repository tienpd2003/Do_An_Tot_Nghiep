package com.shop.product.controller;

import com.shop.product.dto.request.OrderItemRequest;
import com.shop.product.dto.request.OrderRequest;
import com.shop.product.service.impl.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vuongdev.common.dto.response.IDResponse;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.ResponseUtils;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
  private final InventoryServiceImpl inventoryService;

  @PostMapping("/reduce")
  public ResponseEntity<Response<IDResponse<String>>> reduce(@RequestBody OrderRequest orderRequest) {
    inventoryService.reduceInventory(orderRequest);
    IDResponse<String> response = IDResponse.<String>builder()
        .id(String.valueOf(orderRequest.getUserId()))
        .build();
    return ResponseUtils.success(response);
  }
}
