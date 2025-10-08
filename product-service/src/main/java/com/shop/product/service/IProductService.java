package com.shop.product.service;

import com.shop.product.dto.request.ProductRequest;
import com.shop.product.entity.BaseProduct;

public interface IProductService {
    BaseProduct createProduct(ProductRequest baseProduct);
}
