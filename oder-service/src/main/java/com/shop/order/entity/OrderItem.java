package com.shop.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vuongdev.common.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "tbl_order_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id", nullable = false, unique = true)
  private Long orderItemId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "quantity", nullable = false, length = 100)
  private Long quantity;

}
