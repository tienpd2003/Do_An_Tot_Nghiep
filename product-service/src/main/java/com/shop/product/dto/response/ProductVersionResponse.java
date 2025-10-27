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
public class ProductVersionResponse {
    private Long productVersionId;
    private String versionName;
    private String productName;
    private String versionUrl;
    private List<ProductColorResponse> colors;
}

