package com.shop.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVersionRequest {
  private Long productId;
  private String versionName;
  private String description;
  private Long price;
  private boolean isBaseVersion;
}
