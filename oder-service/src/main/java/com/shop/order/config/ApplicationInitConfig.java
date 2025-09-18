package com.shop.order.config;

import com.shop.order.entity.Inventory;
import com.shop.order.entity.Product;
import com.shop.order.repository.InventoryRepository;
import com.shop.order.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                                      InventoryRepository inventoryRepository) {
    log.info("Initializing application.....");
    return args -> {

      if (productRepository.findProductByName("SP1").isEmpty()) {
        Product product = Product.builder()
                .description("SP test")
                .name("SP1")
                .price(20000L)
                .build();
        productRepository.save(product);
        Inventory inventory = Inventory.builder()
                .quantity(1L)
                .product(product)
                .build();
        inventoryRepository.save(inventory);
      }

      if (productRepository.findProductByName("SP2").isEmpty()) {
        Product product = Product.builder()
                .description("SP test 2")
                .name("SP2")
                .price(30000L)
                .build();
        productRepository.save(product);
        Inventory inventory = Inventory.builder()
                .quantity(1L)
                .product(product)
                .build();
        inventoryRepository.save(inventory);
      }
      log.info("Application initialization completed .....");
    };
  }
}

