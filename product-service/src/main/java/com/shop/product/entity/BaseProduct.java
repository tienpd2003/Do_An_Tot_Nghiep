package com.shop.product.entity;

import com.shop.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.vuongdev.common.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "tbl_products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseProduct extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false, unique = true)
  private Long productId;

  @Column(name = "img_url", length = 255)
  private String imgUrl;

  @Column(name = "product_name", nullable = false, length = 100, unique = true)
  private String name;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "price", nullable = false)
  private Long price;

  @Column(name = "warranty_months", nullable = false)
  private Integer warrantyMonths;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable=false)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id", nullable=false)
  private Brand brand;

  @Enumerated(EnumType.STRING)
  @Column(name = "product_status", nullable = false)
  private ProductStatus productStatus;
}
