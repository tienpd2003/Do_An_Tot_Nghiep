package com.shop.product.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tbl_product_attributes")
@Builder
@Entity
public class ProductAttribute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attribute_id", nullable = false)
  private Long attributeId;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 255)
  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_version_id", nullable = false)
  private ProductVersion productVersion;

  @Column(name = "attribute_group", length = 100)
  private String attributeGroup;
}
