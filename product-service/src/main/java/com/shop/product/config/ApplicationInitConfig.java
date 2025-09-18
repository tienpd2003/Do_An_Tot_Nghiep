package com.shop.product.config;

import com.shop.product.entity.*;
import com.shop.product.enums.CategoryName;
import com.shop.product.enums.ProductStatus;
import com.shop.product.enums.SmartPhoneBrand;
import com.shop.product.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
  @Bean
  @ConditionalOnProperty(
          prefix = "spring",
          value = "datasource.driver-class-name",
          havingValue = "com.mysql.cj.jdbc.Driver")
  @Transactional
  ApplicationRunner applicationRunner(ProductRepository productRepository,
                                      ProductAttributeRepository productAttributeRepository,
                                      CategoryRepository categoryRepository,
                                      InventoryRepository inventoryRepository,
                                      BrandRepository brandRepository,
                                      BrandCategoryRepository brandCategoryRepository) {
    log.info("Initializing application.....");
    return args -> {

      if (productRepository.findByName("SP test").isEmpty()) {
        List<Category> categories = new ArrayList<>();
        List<Brand> brands = new ArrayList<>();
        List<BrandCategory> brandCategories = new ArrayList<>();

        for (CategoryName categoryName : CategoryName.values()) {
          categories.add(
                  Category.builder()
                          .categoryName(categoryName.getName())
                          .build()
          );
        }
        categoryRepository.saveAll(categories);
        Category smartphoneCategory = categoryRepository.findByCategoryName(CategoryName.SMARTPHONE.getName()).orElseThrow();
        for (SmartPhoneBrand smartPhoneBrand : SmartPhoneBrand.values()) {
          Brand brand = Brand.builder()
                  .name(smartPhoneBrand.getName())
                  .build();
          brands.add(brand);
          brandCategories.add(
                  BrandCategory.builder()
                          .brand(brand)
                          .category(smartphoneCategory)
                          .build()
          );
        }

        brandRepository.saveAll(brands);
        brandCategoryRepository.saveAll(brandCategories);

        Brand brand = brandRepository.findByName(SmartPhoneBrand.APPLE.getName()).orElseThrow();

        Map<String, String> smartPhoneAttributes = Map.ofEntries(
                Map.entry("CPU", "Snapdragon 888"),
                Map.entry("RAM", "8GB"),
                Map.entry("Storage", "128GB"),
                Map.entry("Camera", "108MP"),
                Map.entry("Battery", "4500mAh"),
                Map.entry("OS", "Android 11"),
                Map.entry("Screen Size", "6.5 inches"),
                Map.entry("Screen Resolution", "2400x1080"),
                Map.entry("Features", "5G, NFC, Fast Charging"),
                Map.entry("Model", "Test Model"),
                Map.entry("Color", "Black"),
                Map.entry("Weight", "180g"),
                Map.entry("Dimensions", "160 x 75 x 8 mm")
        );

        BaseProduct product = BaseProduct.builder()
                .name("SP test")
                .imgUrl("https://example.com/smartphone.jpg")
                .description("Smartphone test")
                .price(10000000L)
                .warrantyMonths(12)
                .category(smartphoneCategory)
                .brand(brand)
                .productStatus(ProductStatus.ACTIVE)
                .build();

        productRepository.save(product);
        buildAttributes(product, smartPhoneAttributes, productAttributeRepository);

        inventoryRepository.save(
                Inventory.builder()
                        .quantity(1L)
                        .product(product)
                        .build()
        );
      }
      log.info("Application initialization completed .....");
    };
  }

  public static void buildAttributes(BaseProduct product, Map<String, String> attributes, ProductAttributeRepository productAttributeRepository) {
    attributes.forEach((key, value) -> productAttributeRepository.save(
            ProductAttribute.builder()
                    .name(key)
                    .value(value)
                    .product(product)
                    .build()));
  }
}


