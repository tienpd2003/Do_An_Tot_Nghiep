package com.shop.product.controller;

import com.shop.product.dto.request.ProductVersionRequest;
import com.shop.product.entity.ProductVersion;
import com.shop.product.service.IProductVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.ResponseUtils;

import java.util.List;

@RestController
@RequestMapping("/products/versions")
@RequiredArgsConstructor
public class ProductVersionController {
    private final IProductVersionService productVersionService;

    @PostMapping("/create")
    public ResponseEntity<Response<ProductVersion>> create(@RequestBody ProductVersionRequest request) {
        ProductVersion created = productVersionService.createProductVersion(request);
        return ResponseUtils.success(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductVersion>> get(@PathVariable Long id) {
        ProductVersion v = productVersionService.getProductVersion(id);
        return ResponseUtils.success(v);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductVersion>> update(@PathVariable Long id, @RequestBody ProductVersionRequest request) {
        ProductVersion updated = productVersionService.updateProductVersion(id, request);
        return ResponseUtils.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> delete(@PathVariable Long id) {
        productVersionService.deleteProductVersion(id);
        return ResponseUtils.success(null);
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductVersion>>> list(@RequestParam Long productId) {
        List<ProductVersion> list = productVersionService.listByProductId(productId);
        return ResponseUtils.success(list);
    }
}
