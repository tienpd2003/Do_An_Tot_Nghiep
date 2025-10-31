package com.shop.product.service;

import com.shop.product.dto.request.ProductAttributeRequest;
import com.shop.product.entity.ProductAttribute;

import java.util.List;

public interface IProductAttributeService {
    ProductAttribute createAttribute(ProductAttributeRequest request);

    ProductAttribute getAttribute(Long id);

    ProductAttribute updateAttribute(Long id, ProductAttributeRequest request);

    void deleteAttribute(Long id);

    List<ProductAttribute> listByProductVersionId(Long productVersionId);
}

