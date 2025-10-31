package com.shop.product.service;

import com.shop.product.dto.request.ProductVersionRequest;
import com.shop.product.entity.ProductVersion;

import java.util.List;

public interface IProductVersionService {
    ProductVersion createProductVersion(ProductVersionRequest productVersionRequest);

    ProductVersion getProductVersion(Long id);

    ProductVersion updateProductVersion(Long id, ProductVersionRequest request);

    void deleteProductVersion(Long id);

    List<ProductVersion> listByProductId(Long productId);
}
