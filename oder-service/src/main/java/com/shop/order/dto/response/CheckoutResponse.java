package com.shop.order.dto.response;

import com.shop.order.enums.StatusStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponse {
  private String ticketId;
  private StatusStock status;
  private String message;
}
