package com.shop.order.entity;

import com.shop.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.vuongdev.common.model.BaseEntity;

import java.util.List;

@Entity
@Table(name = "tbl_orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id", nullable = false, unique = true)
  private Long id;

  @Column(name = "order_code", nullable = false, unique = true)
  private String orderCode;

  @Column(name = "ticket_id", unique = true, nullable = false)
  private String ticketId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @Column(name = "total_amount",nullable = false)
  private Long totalAmount;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItem> items;
}
