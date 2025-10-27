package com.shop.product.entity;

import com.shop.product.converter.StringListJsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "tbl_product_colors")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_color_id", nullable = false)
    private Long productColorId;

    @Column(name = "color_name", nullable = false, length = 100)
    private String colorName;

    @Column(name = "color_code", length = 7)
    private String colorCode;

    @Column(name = "color_url", length = 500)
    private String colorUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;

    @Column(name = "image_urls", columnDefinition = "json")
    @Convert(converter = StringListJsonConverter.class)
    private List<String> imageUrls;

    @OneToOne(mappedBy = "productColor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Price price;
}
