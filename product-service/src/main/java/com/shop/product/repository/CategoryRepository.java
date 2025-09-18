package com.shop.product.repository;

import com.shop.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
  Optional<Category> findByCategoryName(String name);
}
