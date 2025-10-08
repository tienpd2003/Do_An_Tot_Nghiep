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
  ApplicationRunner applicationRunner(CategoryRepository categoryRepository,
                                      BrandRepository brandRepository,
                                      BrandCategoryRepository brandCategoryRepository) {
    log.info("Initializing application.....");
    return args -> {
        if (categoryRepository.count() == 0 && brandRepository.count() == 0) {
            createCategoryAndBrand(categoryRepository, brandRepository, brandCategoryRepository);
        }
      log.info("Application initialization completed .....");
    };
  }

  public void createCategoryAndBrand(CategoryRepository categoryRepository,
                                 BrandRepository brandRepository,
                                 BrandCategoryRepository brandCategoryRepository) {
    List<Category> categories = new ArrayList<>();
    for (CategoryName value : CategoryName.values()) {
      Category category = Category.builder()
              .categoryName(value.getName())
              .build();
      categories.add(category);
    }
    categoryRepository.saveAll(categories);
    log.info("Categories initialized");

    List<Brand> brands = new ArrayList<>();
    for (SmartPhoneBrand value : SmartPhoneBrand.values()) {
      Brand brand = Brand.builder()
              .name(value.getName())
              .description(value.getName() + " description")
              .build();
      brands.add(brand);
    }
    brandRepository.saveAll(brands);
    log.info("Brands initialized");

    Map<String, Category> categoryMap = categoryRepository.findAll().stream()
            .collect(java.util.stream.Collectors.toMap(Category::getCategoryName, c -> c));
    Map<String, Brand> brandMap = brandRepository.findAll().stream()
            .collect(java.util.stream.Collectors.toMap(Brand::getName, b -> b));

    List<BrandCategory> brandCategories = new ArrayList<>();
    for (SmartPhoneBrand value : SmartPhoneBrand.values()) {
      BrandCategory brandCategory = BrandCategory.builder()
              .brand(brandMap.get(value.getName()))
              .category(categoryMap.get(CategoryName.SMARTPHONE.getName()))
              .build();
      brandCategories.add(brandCategory);
    }
    brandCategoryRepository.saveAll(brandCategories);
    log.info("Brand-Categories initialized");
  }
}


