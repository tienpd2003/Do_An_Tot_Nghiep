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

  @Column(name = "version_name", nullable = false, length = 50)
  private String versionName;

  @Column(name = "product_name", nullable = false, length = 200)
  private String productName;

  @Column(name = "version_url", length = 500)
  private String versionUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private BaseProduct baseProduct;

  @OneToMany(mappedBy = "productVersion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductColor> colors;
}
