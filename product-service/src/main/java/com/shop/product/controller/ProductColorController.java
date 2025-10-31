package com.shop.product.controller;

import com.shop.product.dto.request.ProductColorRequest;
import com.shop.product.entity.ProductColor;
import com.shop.product.service.IProductColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.ResponseUtils;

import java.util.List;

@RestController
@RequestMapping("/products/colors")
@RequiredArgsConstructor
public class ProductColorController {
    private final IProductColorService productColorService;

    @PostMapping("/create")
    public ResponseEntity<Response<Object>> create(@RequestBody ProductColorRequest request) {
        productColorService.createProductColor(request);
        return ResponseUtils.success(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductColor>> get(@PathVariable Long id) {
        ProductColor c = productColorService.getProductColor(id);
        return ResponseUtils.success(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductColor>> update(@PathVariable Long id, @RequestBody ProductColorRequest request) {
        ProductColor updated = productColorService.updateProductColor(id, request);
        return ResponseUtils.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> delete(@PathVariable Long id) {
        productColorService.deleteProductColor(id);
        return ResponseUtils.success(null);
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductColor>>> list(@RequestParam Long productVersionId) {
        List<ProductColor> list = productColorService.listByProductVersionId(productVersionId);
        return ResponseUtils.success(list);
    }
}
