package com.shop.product.dto.response;

import com.shop.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseProductResponse {
    private Long productId;
    private String baseProductName;
    private String description;
    private String sourceUrl;
    private ProductStatus productStatus;
    private String categoryName;
    private String brandName;
    private List<ProductVersionResponse> versions;
    private List<ProductAttributeResponse> baseAttributes;
    private Double averageRating;
    private Integer reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

