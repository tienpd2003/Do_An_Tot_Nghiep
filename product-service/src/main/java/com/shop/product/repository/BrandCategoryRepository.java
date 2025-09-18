package com.shop.product.repository;

import com.shop.product.entity.BrandCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandCategoryRepository extends JpaRepository<BrandCategory, Long> {
}
