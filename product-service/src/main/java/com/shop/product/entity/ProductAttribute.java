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

  @Column(name = "attribute_name", nullable = false, length = 100)
  private String name;

  @Column(name = "attribute_value", nullable = false, columnDefinition = "TEXT")
  private String value;

  @Column(name = "type_attribute", nullable = false, length = 100)
  private String typeAttribute;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private BaseProduct baseProduct;
}
