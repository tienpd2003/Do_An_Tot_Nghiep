package com.shop.product.repository;

import com.shop.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
  Optional<Brand> findByName(String name);
}
