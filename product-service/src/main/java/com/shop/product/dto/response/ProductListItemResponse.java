package com.shop.product.dto.response;

import com.shop.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductListItemResponse {
    private Long productId;
    private String baseProductName;
    private String description;
    private String imageUrl; // First image from first color
    private String minPrice; // Lowest price among all versions/colors
    private String maxPrice; // Highest price
    private ProductStatus productStatus;
    private String categoryName;
    private String brandName;
    private Double averageRating;
    private Integer reviewCount;
    private boolean inStock;
}

