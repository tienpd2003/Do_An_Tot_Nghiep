package com.shop.product.service;

import com.shop.product.dto.request.ProductVersionRequest;
import com.shop.product.entity.ProductVersion;

public interface IProductVersionService {
    ProductVersion createProductVersion(ProductVersionRequest productVersionRequest);
}
