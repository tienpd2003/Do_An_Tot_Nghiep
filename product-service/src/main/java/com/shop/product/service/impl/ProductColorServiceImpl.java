package com.shop.product.service.impl;

import com.shop.product.dto.request.ProductColorRequest;
import com.shop.product.entity.ProductColor;
import com.shop.product.entity.ProductVersion;
import com.shop.product.enums.ProductErrorCode;
import com.shop.product.repository.ProductColorRepository;
import com.shop.product.repository.ProductVersionRepository;
import com.shop.product.service.IProductColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vuongdev.common.exception.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductColorServiceImpl implements IProductColorService {
    private final ProductColorRepository productColorRepository;
    private final ProductVersionRepository productVersionRepository;

    @Override
    public void createProductColor(ProductColorRequest productColorRequest) {
        ProductVersion version = productVersionRepository.findById(productColorRequest.getProductVersionId())
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_VERSION_NOT_FOUND));

        ProductColor c = new ProductColor();
        c.setColorName(productColorRequest.getColorName());
        c.setColorCode(productColorRequest.getColorCode());
        c.setImageUrls(productColorRequest.getImageUrls());
        c.setProductVersion(version);

        productColorRepository.save(c);
    }

    @Override
    public ProductColor getProductColor(Long id) {
        return productColorRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_COLOR_NOT_FOUND));
    }

    @Override
    public ProductColor updateProductColor(Long id, ProductColorRequest request) {
        ProductColor existing = productColorRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_COLOR_NOT_FOUND));

        if (request.getColorName() != null) existing.setColorName(request.getColorName());
        if (request.getColorCode() != null) existing.setColorCode(request.getColorCode());
        if (request.getImageUrls() != null) existing.setImageUrls(request.getImageUrls());

        return productColorRepository.save(existing);
    }

    @Override
    public void deleteProductColor(Long id) {
        ProductColor existing = productColorRepository.findById(id)
                .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_COLOR_NOT_FOUND));
        productColorRepository.delete(existing);
    }

    @Override
    public List<ProductColor> listByProductVersionId(Long productVersionId) {
        return productColorRepository.findByProductVersion_ProductVersionId(productVersionId);
    }
}

