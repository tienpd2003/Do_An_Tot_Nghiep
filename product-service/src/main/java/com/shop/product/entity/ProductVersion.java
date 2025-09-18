package com.shop.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

  @Column(name = "version", nullable = false, length = 50)
  private String version;

  @Column(name = "description", length = 255)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private BaseProduct product;

  @Column(name = "images", columnDefinition = "TEXT")
  private List<String> images;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "previous_version_id")
  private ProductVersion previousVersion;
}
