package com.shop.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAttributeResponse {
    private Long attributeId;
    private String attributeName;
    private String attributeValue;
}

