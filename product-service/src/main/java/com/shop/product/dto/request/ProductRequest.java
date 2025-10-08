package com.shop.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
  private String name;
  private String imgUrl;
  private String description;
  private Long price;
  private String categoryName;
  private String brandName;
  private Integer warrantyMonths;
  private Long quantity;
}
