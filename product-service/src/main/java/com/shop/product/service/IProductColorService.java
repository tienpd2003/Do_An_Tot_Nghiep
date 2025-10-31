package com.shop.product.service;

import com.shop.product.dto.request.ProductColorRequest;
import com.shop.product.entity.ProductColor;

import java.util.List;

public interface IProductColorService {
    void createProductColor(ProductColorRequest productColorRequest);

    ProductColor getProductColor(Long id);

    ProductColor updateProductColor(Long id, ProductColorRequest request);

    void deleteProductColor(Long id);

    List<ProductColor> listByProductVersionId(Long productVersionId);
}
