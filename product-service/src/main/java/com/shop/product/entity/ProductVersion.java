package com.shop.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "tbl_product_versions")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class ProductVersion {
  @Column(name = "product_version_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long productVersionId;

  @Column(name = "version_code", nullable = false, unique = true)
  private UUID versionCode = UUID.randomUUID();

  @Column(name = "version", nullable = false, length = 50)
  private String versionName;

  @Column(name = "description", length = 255)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private BaseProduct product;

  @Column(name = "price", nullable = false)
  private Long price;

  @Column(name = "is_base_version", nullable = false)
  private boolean isBaseVersion;
}
