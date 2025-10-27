package com.shop.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_reviews")
@Entity
@Builder
@Getter
@Setter
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id", nullable = false)
  private Long reviewId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "review_content", nullable = false, columnDefinition = "TEXT")
  private String reviewContent;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private BaseProduct baseProduct;
}
