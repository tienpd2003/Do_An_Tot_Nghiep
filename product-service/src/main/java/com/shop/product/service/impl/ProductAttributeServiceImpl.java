package com.shop.product.service.impl;

import com.shop.product.dto.request.ProductAttributeRequest;
import com.shop.product.entity.ProductAttribute;
import com.shop.product.entity.ProductVersion;
import com.shop.product.enums.ProductErrorCode;
import com.shop.product.repository.ProductAttributeRepository;
import com.shop.product.repository.ProductVersionRepository;
import com.shop.product.service.IProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vuongdev.common.exception.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements IProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductVersionRepository productVersionRepository;

    @Override
    public ProductAttribute createAttribute(ProductAttributeRequest request) {
        ProductVersion version = productVersionRepository.findById(request.getProductVersionId())
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));

        ProductAttribute a = new ProductAttribute();
        a.setName(request.getAttributeName());
        a.setValue(request.getAttributeValue());
        a.setProductVersion(version);

        return productAttributeRepository.save(a);
    }

    @Override
    public ProductAttribute getAttribute(Long id) {
        return productAttributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_ATTRIBUTE_NOT_FOUND));
    }

    @Override
    public ProductAttribute updateAttribute(Long id, ProductAttributeRequest request) {
        ProductAttribute existing = productAttributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_ATTRIBUTE_NOT_FOUND));

        if (request.getAttributeName() != null) existing.setName(request.getAttributeName());
        if (request.getAttributeValue() != null) existing.setValue(request.getAttributeValue());

        return productAttributeRepository.save(existing);
    }

    @Override
    public void deleteAttribute(Long id) {
        ProductAttribute existing = productAttributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_ATTRIBUTE_NOT_FOUND));
        productAttributeRepository.delete(existing);
    }

    @Override
    public List<ProductAttribute> listByProductVersionId(Long productVersionId) {
        return productAttributeRepository.findByProductVersion_ProductVersionId(productVersionId);
    }
}

