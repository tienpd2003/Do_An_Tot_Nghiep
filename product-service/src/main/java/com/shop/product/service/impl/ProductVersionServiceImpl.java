package com.shop.product.service.impl;

import com.shop.product.dto.request.ProductVersionRequest;
import com.shop.product.entity.BaseProduct;
import com.shop.product.entity.ProductVersion;
import com.shop.product.enums.ProductErrorCode;
import com.shop.product.repository.BaseProductRepository;
import com.shop.product.repository.ProductVersionRepository;
import com.shop.product.service.IProductVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vuongdev.common.exception.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVersionServiceImpl implements IProductVersionService {
    private final ProductVersionRepository productVersionRepository;
    private final BaseProductRepository baseProductRepository;

    @Override
    public ProductVersion createProductVersion(ProductVersionRequest productVersionRequest) {
        BaseProduct product = baseProductRepository.findById(productVersionRequest.getProductId())
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));

        ProductVersion v = new ProductVersion();
        v.setProduct(product);
        v.setVersionName(productVersionRequest.getVersionName());
        v.setDescription(productVersionRequest.getDescription());
        v.setPrice(productVersionRequest.getPrice());
        v.setBaseVersion(productVersionRequest.isBaseVersion());

        return productVersionRepository.save(v);
    }

    @Override
    public ProductVersion getProductVersion(Long id) {
        return productVersionRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductVersion updateProductVersion(Long id, ProductVersionRequest request) {
        ProductVersion existing = productVersionRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (request.getVersionName() != null) existing.setVersionName(request.getVersionName());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getPrice() != null) existing.setPrice(request.getPrice());
        existing.setBaseVersion(request.isBaseVersion());

        return productVersionRepository.save(existing);
    }

    @Override
    public void deleteProductVersion(Long id) {
        ProductVersion existing = productVersionRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));
        productVersionRepository.delete(existing);
    }

    @Override
    public List<ProductVersion> listByProductId(Long productId) {
        return productVersionRepository.findByProduct_ProductId(productId);
    }
}
