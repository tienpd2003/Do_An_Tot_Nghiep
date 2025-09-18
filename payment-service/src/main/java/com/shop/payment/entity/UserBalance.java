package com.shop.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_balances")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBalance {
  @Id
  private String id;
  private Long userId;
  private Long balance;
}
