package com.shop.product.repository;

import com.shop.product.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findByProductVersion_ProductVersionId(Long productVersionId);
}
