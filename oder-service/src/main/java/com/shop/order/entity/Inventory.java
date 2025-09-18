package com.shop.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vuongdev.common.model.BaseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_inventory")
@Entity
public class Inventory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "inventory_id", nullable = false, unique = true)
  private Long inventoryId;

  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @OneToOne
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private Product product;

  @Version
  private Long version;
}
