package com.shop.product.repository;

import com.shop.product.entity.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseProductRepository extends JpaRepository<BaseProduct, Long> {

  Optional<BaseProduct> findByName(String name);
}
