package com.shop.product.repository;

import com.shop.product.entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
    List<ProductVersion> findByProduct_ProductId(Long productId);
}
