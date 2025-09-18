package com.shop.product.repository;

import com.shop.product.entity.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<BaseProduct, Long> {
  Optional<BaseProduct> findByName(String name);
}
