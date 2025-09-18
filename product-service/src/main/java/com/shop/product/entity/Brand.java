package com.shop.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tbl_brands")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Brand {
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Id
  @Column(name = "brand_id", nullable = false, unique = true)
  private Long brandId;

  @Column(name = "brand_name", nullable = false, length = 100, unique = true)
  private String name;

  @Column(name = "description", length = 500)
  private String description;
}
