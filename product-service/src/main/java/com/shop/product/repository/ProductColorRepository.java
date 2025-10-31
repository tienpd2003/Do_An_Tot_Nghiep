package com.shop.product.repository;

import com.shop.product.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
    List<ProductColor> findByProductVersion_ProductVersionId(Long productVersionId);
}