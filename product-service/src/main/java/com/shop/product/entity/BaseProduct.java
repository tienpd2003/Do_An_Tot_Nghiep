package com.shop.product.entity;

import com.shop.product.enums.ProductStatus;
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
public class BaseProduct extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false, unique = true)
  private Long productId;

  @Column(name = "base_product_name", nullable = false, length = 200, unique = true)
  private String baseProductName;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "source_url", length = 500)
  private String sourceUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable=false)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id", nullable=false)
  private Brand brand;

  @Enumerated(EnumType.STRING)
  @Column(name = "product_status", nullable = false)
  private ProductStatus productStatus;

  @OneToMany(mappedBy = "baseProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductVersion> versions;

  @OneToMany(mappedBy = "baseProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductAttribute> baseAttributes;

  @OneToMany(mappedBy = "baseProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Review> reviews;
}
