package com.shop.product.controller;

import com.shop.product.dto.request.ProductAttributeRequest;
import com.shop.product.entity.ProductAttribute;
import com.shop.product.service.IProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.ResponseUtils;

import java.util.List;

@RestController
@RequestMapping("/products/attributes")
@RequiredArgsConstructor
public class ProductAttributeController {
    private final IProductAttributeService attributeService;

    @PostMapping("/create")
    public ResponseEntity<Response<ProductAttribute>> create(@RequestBody ProductAttributeRequest request) {
        ProductAttribute created = attributeService.createAttribute(request);
        return ResponseUtils.success(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductAttribute>> get(@PathVariable Long id) {
        ProductAttribute a = attributeService.getAttribute(id);
        return ResponseUtils.success(a);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductAttribute>> update(@PathVariable Long id, @RequestBody ProductAttributeRequest request) {
        ProductAttribute updated = attributeService.updateAttribute(id, request);
        return ResponseUtils.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> delete(@PathVariable Long id) {
        attributeService.deleteAttribute(id);
        return ResponseUtils.success(null);
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductAttribute>>> list(@RequestParam Long productVersionId) {
        List<ProductAttribute> list = attributeService.listByProductVersionId(productVersionId);
        return ResponseUtils.success(list);
    }
}

