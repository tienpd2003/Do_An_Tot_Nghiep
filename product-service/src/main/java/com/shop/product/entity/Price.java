package com.shop.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_prices")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", nullable = false)
    private Long priceId;

    @Column(name = "price_present", length = 50)
    private String pricePresent;

    @Column(name = "price_old", length = 50)
    private String priceOld;

    @Column(name = "price_percent", length = 10)
    private String pricePercent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_color_id", nullable = false, unique = true)
    private ProductColor productColor;
}
