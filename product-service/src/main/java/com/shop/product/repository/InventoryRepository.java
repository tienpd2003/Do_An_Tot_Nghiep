package com.shop.product.repository;

import com.shop.product.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
  @Modifying
  @Transactional
  @Query("update Inventory i set i.quantity = i.quantity - ?2 where i.product.productId = ?1 and i.quantity >= ?2")
  int reduceInventory(Long productId, Integer quantity);
}
