package com.shop.product.repository;

import com.shop.product.entity.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<BaseProduct, Long>, JpaSpecificationExecutor<BaseProduct> {
  Optional<BaseProduct> findByBaseProductName(String baseProductName);
  boolean existsByBaseProductName(String baseProductName);
}
