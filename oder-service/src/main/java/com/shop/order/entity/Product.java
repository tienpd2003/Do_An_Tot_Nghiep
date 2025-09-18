package com.shop.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.vuongdev.common.model.BaseEntity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false, unique = true)
  private Long productId;

  @Column(name = "product_name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  @Column(name = "price", nullable = false)
  private Long price;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderItem> orderItems;
}
