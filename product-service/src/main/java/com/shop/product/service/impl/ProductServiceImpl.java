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
import org.vuongdev.common.exception.AppException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;
  private final InventoryRepository inventoryRepository;

  @Override
  public BaseProduct createProduct(ProductRequest baseProduct) {
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

    return productRepository.save(product);
  }
}
