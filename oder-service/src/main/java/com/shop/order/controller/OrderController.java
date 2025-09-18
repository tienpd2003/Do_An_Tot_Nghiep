package com.shop.order.controller;

import com.shop.order.constant.Message;
import com.shop.order.dto.request.OrderRequest;
import com.shop.order.kafka.OrderOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vuongdev.common.dto.response.IDResponse;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.LocalizationUtils;
import org.vuongdev.common.utils.ResponseUtils;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderOrchestrator orchestrator;
  private final LocalizationUtils localizationUtils;

//  @PostMapping("/checkout")
//  public ResponseEntity<Response<IDResponse<String>>> checkout(@Valid @RequestBody OrderRequest orderRequest){
//    CheckoutResponse checkoutResponse = checkoutService.checkout(orderRequest);
//    IDResponse<String> response = IDResponse.<String>builder()
//        .id(checkoutResponse.getTicketId())
//        .build();
//    return ResponseUtils.success(response, localizationUtils.getLocalizedMessage(Message.ORDER_CHECKOUT_SUCCESS));
//  }

  @PostMapping("/startOrder")
  public ResponseEntity<Response<IDResponse<String>>> startOrder(@RequestBody OrderRequest orderRequest) {
    String ticketId = orchestrator.startOrder(orderRequest.getUserId(), orderRequest.getOrderItemRequests());
    IDResponse<String> response = IDResponse.<String>builder()
        .id(ticketId)
        .build();
    return ResponseUtils.success(response, localizationUtils.getLocalizedMessage(Message.ORDER_CREATED));
  }
}
