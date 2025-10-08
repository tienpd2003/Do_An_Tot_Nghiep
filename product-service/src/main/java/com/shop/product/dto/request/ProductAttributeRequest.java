package com.shop.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAttributeRequest {
    private String attributeName;
    private String attributeValue;
    private Long productVersionId;
}
