package com.shop.product.service;

import com.shop.product.dto.request.ProductRequest;
import com.shop.product.entity.BaseProduct;
import org.vuongdev.common.dto.response.IDResponse;

import java.util.List;

public interface IProductService {
    IDResponse<Long> createBaseProduct(ProductRequest baseProduct);

    // CRUD for BaseProduct
    BaseProduct getBaseProduct(Long id);

    BaseProduct updateBaseProduct(Long id, ProductRequest request);

    void deleteBaseProduct(Long id);

    List<BaseProduct> listBaseProducts();
}
