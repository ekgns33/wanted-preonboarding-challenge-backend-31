package org.ekgns33.commerce.product.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ekgns33.commerce.common.response.ApiPageData;
import org.ekgns33.commerce.common.response.ApiSuccessResponse;
import org.ekgns33.commerce.product.api.dto.ProductRequestMapper;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest;
import org.ekgns33.commerce.product.api.dto.ProductSearchRequest;
import org.ekgns33.commerce.product.service.ProductQueryService;
import org.ekgns33.commerce.product.service.ProductCommandService;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand;
import org.ekgns33.commerce.product.service.dto.query.product.ProductDetailQueryResponse;
import org.ekgns33.commerce.product.service.dto.ProductSaveResponse;
import org.ekgns33.commerce.product.service.dto.SearchedProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductCommandService productCommandService;
  private final ProductQueryService productQueryService;
  private final ProductRequestMapper productRequestMapper;

  @PostMapping
  public ResponseEntity<ApiSuccessResponse<ProductSaveResponse>> createProduct(
      @Valid @RequestBody ProductSaveRequest productSaveRequest) {
    log.info("Product Save Request: {}", productSaveRequest);
    ProductCreateCommand command = productRequestMapper.toCommand(productSaveRequest);
    ProductSaveResponse response = productCommandService.createProduct(command);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiSuccessResponse.of(response, "상품이 성공적으로 등록되었습니다."));
  }

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<ApiPageData<SearchedProductDto>>> searchProducts(
      @ModelAttribute ProductSearchRequest request
  ) {
    ApiPageData<SearchedProductDto> response = productQueryService.searchProducts(
        request.toQuery()
    );

    return ResponseEntity.ok(
        ApiSuccessResponse.of(
            response,
            "상품 목록을 성공적으로 조회했습니다."
        )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<ProductDetailQueryResponse>> getProductDetail(
      @PathVariable Long id
  ) {
    ProductDetailQueryResponse productDetailResponse = productQueryService.getProductDetail(id);
    return ResponseEntity.ok(
        ApiSuccessResponse.of(
            productDetailResponse,
            "상품 상세 정보를 성공적으로 조회했습니다."
        )
    );
  }
}
