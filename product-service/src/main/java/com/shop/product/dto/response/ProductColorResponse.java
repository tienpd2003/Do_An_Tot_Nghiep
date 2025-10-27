package com.shop.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductColorResponse {
    private Long productColorId;
    private String colorName;
    private String colorCode;
    private String colorUrl;
    private List<String> imageUrls;
    private PriceResponse price;
    private InventoryResponse inventory;
}

