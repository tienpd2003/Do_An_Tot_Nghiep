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

    @Column(name = "color_name", nullable = false)
    private String colorName;

    @Column(name = "color_code", nullable = false, length = 7)
    private String colorCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;

    @Column(name = "image_url", nullable = false, columnDefinition = "json")
    @Convert(converter = StringListJsonConverter.class)
    private List<String> imageUrl;
}
