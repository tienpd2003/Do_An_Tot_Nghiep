package com.shop.product.service;

import com.shop.product.dto.request.ProductColorRequest;
import com.shop.product.entity.ProductColor;

public interface IProductColorService {
    void createProductColor(ProductColorRequest productColorRequest);
}
