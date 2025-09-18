package com.shop.order.repository;

import com.shop.order.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  @Query("SELECT i FROM Inventory i WHERE i.product.productId in ?1")
  List<Inventory> findAllByProductIdIn(List<Long> productIds);

  Optional<Inventory> findByProduct_ProductId(Long productId);
}
