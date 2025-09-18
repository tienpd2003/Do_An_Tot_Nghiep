package com.shop.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.Check;
import org.vuongdev.common.model.BaseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_inventory")
@Entity
@Check(constraints = "quantity >= 0")
public class Inventory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "inventory_id", nullable = false, unique = true)
  private Long inventoryId;

  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @OneToOne
  @JoinColumn(name = "product_id", nullable = false, unique = true)
  private BaseProduct product;

  @Version
  private Long version;
}
