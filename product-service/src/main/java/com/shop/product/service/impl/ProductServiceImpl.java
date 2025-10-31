package com.shop.product.service.impl;

import com.shop.product.dto.request.ProductRequest;
import com.shop.product.entity.BaseProduct;
import com.shop.product.entity.Brand;
import com.shop.product.entity.Category;
import com.shop.product.entity.Inventory;
import com.shop.product.enums.ProductErrorCode;
import com.shop.product.enums.ProductStatus;
import com.shop.product.repository.BrandRepository;
import com.shop.product.repository.CategoryRepository;
import com.shop.product.repository.InventoryRepository;
import com.shop.product.repository.ProductRepository;
import com.shop.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vuongdev.common.dto.response.IDResponse;
import org.vuongdev.common.exception.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;
  private final InventoryRepository inventoryRepository;

  @Override
  public IDResponse<Long> createBaseProduct(ProductRequest baseProduct) {
    if (productRepository.existsByName(baseProduct.getName())) {
      throw new AppException(ProductErrorCode.PRODUCT_EXIST);
    }

    Brand brand = brandRepository.findByName(baseProduct.getBrandName())
            .orElseThrow(() -> new AppException(ProductErrorCode.BRAND_NOT_FOUND));

    Category category = categoryRepository.findByCategoryName(baseProduct.getCategoryName())
            .orElseThrow(() -> new AppException(ProductErrorCode.CATEGORY_NOT_FOUND));

    BaseProduct product = BaseProduct.builder()
            .name(baseProduct.getName())
            .description(baseProduct.getDescription())
            .price(baseProduct.getPrice())
            .brand(brand)
            .category(category)
            .productStatus(ProductStatus.ACTIVE)
            .imgUrl(baseProduct.getImgUrl())
            .warrantyMonths(baseProduct.getWarrantyMonths())
            .build();

    Inventory inventory = Inventory.builder()
            .product(product)
            .quantity(baseProduct.getQuantity())
            .build();
    inventoryRepository.save(inventory);
    BaseProduct savedProduct = productRepository.save(product);
    return new IDResponse<>(savedProduct.getProductId());
  }

  @Override
  public BaseProduct getBaseProduct(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));
  }

  @Override
  public BaseProduct updateBaseProduct(Long id, ProductRequest request) {
    BaseProduct existing = productRepository.findById(id)
        .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));

    if (request.getName() != null && !request.getName().equals(existing.getName())) {
      if (productRepository.existsByName(request.getName())) {
        throw new AppException(ProductErrorCode.PRODUCT_EXIST);
      }
      existing.setName(request.getName());
    }

    if (request.getDescription() != null) existing.setDescription(request.getDescription());
    if (request.getPrice() != null) existing.setPrice(request.getPrice());
    if (request.getImgUrl() != null) existing.setImgUrl(request.getImgUrl());
    if (request.getWarrantyMonths() != null) existing.setWarrantyMonths(request.getWarrantyMonths());

    // update brand if provided
    if (request.getBrandName() != null) {
      Brand brand = brandRepository.findByName(request.getBrandName())
          .orElseThrow(() -> new AppException(ProductErrorCode.BRAND_NOT_FOUND));
      existing.setBrand(brand);
    }

    // update category if provided
    if (request.getCategoryName() != null) {
      Category category = categoryRepository.findByCategoryName(request.getCategoryName())
          .orElseThrow(() -> new AppException(ProductErrorCode.CATEGORY_NOT_FOUND));
      existing.setCategory(category);
    }

    return productRepository.save(existing);
  }

  @Override
  public void deleteBaseProduct(Long id) {
    BaseProduct existing = productRepository.findById(id)
        .orElseThrow(() -> new AppException(ProductErrorCode.PRODUCT_NOT_FOUND));
    productRepository.delete(existing);
  }

  @Override
  public List<BaseProduct> listBaseProducts() {
    return productRepository.findAll();
  }
}
