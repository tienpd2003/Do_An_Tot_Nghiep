package com.shop.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceResponse {
    private Long priceId;
    private String pricePresent;
    private String priceOld;
    private String pricePercent;
}

