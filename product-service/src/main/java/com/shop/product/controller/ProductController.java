package com.shop.product.controller;

import com.shop.product.constants.Message;
import com.shop.product.dto.request.ProductRequest;
import com.shop.product.service.IProductService;
import com.shop.product.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vuongdev.common.constant.Messages;
import org.vuongdev.common.dto.response.IDResponse;
import org.vuongdev.common.dto.response.Response;
import org.vuongdev.common.utils.LocalizationUtils;
import org.vuongdev.common.utils.ResponseUtils;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private IProductService productService;

  @Autowired
  private LocalizationUtils localizationUtils;

  @PostMapping("/create-base-product")
  public ResponseEntity<Response<IDResponse<Long>>> createBaseProduct (@Valid @RequestBody ProductRequest productRequest) {
    IDResponse<Long> idResponse = productService.createBaseProduct(productRequest);
    return ResponseUtils.success(idResponse, localizationUtils.getLocalizedMessage(Messages.CREATE_SUCCESSFULLY));
  }


}