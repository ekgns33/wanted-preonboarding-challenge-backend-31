package org.ekgns33.commerce.product.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ekgns33.commerce.common.response.ApiSuccessResponse;
import org.ekgns33.commerce.product.api.dto.ProductRequestMapper;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest;
import org.ekgns33.commerce.product.service.ProductService;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand;
import org.ekgns33.commerce.product.service.dto.ProductSaveResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductRequestMapper productRequestMapper;

  @PostMapping
  public ResponseEntity<ApiSuccessResponse<ProductSaveResponse>> createProduct(
      @Valid @RequestBody ProductSaveRequest productSaveRequest) {
    log.info("Product Save Request: {}", productSaveRequest);
    ProductCreateCommand command = productRequestMapper.toCommand(productSaveRequest);
    ProductSaveResponse response = productService.createProduct(command);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiSuccessResponse.of(response, "상품이 성공적으로 등록되었습니다."));
  }
}
