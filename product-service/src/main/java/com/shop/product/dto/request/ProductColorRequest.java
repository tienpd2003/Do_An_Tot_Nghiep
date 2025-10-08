package com.shop.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductColorRequest {
  private String colorName;
  private String colorCode;
  private List<String> imageUrls;
  private Long productVersionId;
}
