package org.ekgns33.commerce.product.api;

import static io.restassured.RestAssured.given;
import static org.ekgns33.commerce.common.exception.CommonErrorCode.CONFLICT;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.ekgns33.commerce.common.IntegrationTestSupport;
import org.ekgns33.commerce.common.TruncateDatabaseCleaner;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest.ProductCategoryRequest;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest.ProductDetailRequest;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest.ProductImageRequest;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest.ProductOptionGroupRequest;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest.ProductOptionRequest;
import org.ekgns33.commerce.product.api.dto.ProductSaveRequest.ProductPriceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;


class ProductAcceptanceTest extends IntegrationTestSupport {

  @LocalServerPort
  int serverPort;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TruncateDatabaseCleaner truncateDatabaseCleaner;

  @BeforeEach()
  void setUp() {
    RestAssured.port = serverPort;
    truncateDatabaseCleaner.truncateProductRelatedTables();
  }


  @DisplayName("상품 등록 성공")
  @Test
  void create_product_success() throws JsonProcessingException {

    ProductSaveRequest request = getMockProductSaveRequest(1L, 2L);

    given()
        .contentType("application/json")
        .header("Authorization", "Bearer test-token")
        .body(objectMapper.writeValueAsString(request))
        .log().all()
        .when()
        .post("/api/products")
        .then()
        .log().all()
        .statusCode(HttpStatus.CREATED.value())
        .body("success", equalTo(true))
        .body("data.id", notNullValue())
        .body("data.name", equalTo("원티드 소파"))
        .body("data.slug", equalTo("wanted-sofa"))
        .body("data.created_at", notNullValue())
        .body("data.updated_at", notNullValue())
        .body("message", equalTo("상품이 성공적으로 등록되었습니다."));


  }

  @DisplayName("입력 정보 유효성 검사 실패")
  @Test
  void create_product_failure_invalid_input() throws JsonProcessingException {
    // Arrange
    ProductSaveRequest invalidRequest = getInvalidMockProductSaveRequest();

    // Act & Assert
    given()
        .contentType("application/json")
        .header("Authorization", "Bearer test-token")
        .body(objectMapper.writeValueAsString(invalidRequest))
        .when()
        .post("/api/products")
        .then()
        .log().all()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("success", equalTo(false))
        .body("error.code", equalTo("INVALID_INPUT"))
        .body("error.message", equalTo("입력 데이터가 유효하지 않습니다."))
        .body("error.details.name", equalTo("상품명은 필수 항목입니다."))
        .body("error.details.base_price", equalTo("기본 가격은 0보다 커야 합니다."));
  }

  @DisplayName("상품 등록 실패 - 리소스 충돌")
  @Test
  void create_product_failure_resource_conflict() throws JsonProcessingException {
    // Arrange
    ProductSaveRequest request = getMockProductSaveRequest(1L, 2L);

    given()
        .contentType("application/json")
        .header("Authorization", "Bearer test-token")
        .body(objectMapper.writeValueAsString(request))
        .when()
        .post("/api/products")
        .then()
        .log().all()
        .statusCode(HttpStatus.CREATED.value());

    given()
        .contentType("application/json")
        .header("Authorization", "Bearer test-token")
        .body(objectMapper.writeValueAsString(request))
        .when()
        .post("/api/products")
        .then()
        .log().all()
        .statusCode(HttpStatus.CONFLICT.value())
        .body("success", equalTo(false))
        .body("error.code", equalTo("CONFLICT"))
        .body("error.message", equalTo(CONFLICT.getMessage()));
  }

  @DisplayName("상품 등록 실패 - 리소스 없음")
  @Test
  void create_product_failure_resource_not_found() throws JsonProcessingException {
    // Arrange
    ProductSaveRequest request = getMockProductSaveRequest(100L, 200L);

    given()
        .contentType("application/json")
        .header("Authorization", "Bearer test-token")
        .body(objectMapper.writeValueAsString(request))
        .when()
        .post("/api/products")
        .then()
        .log().all()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }



  private ProductSaveRequest getInvalidMockProductSaveRequest() {
    ProductDetailRequest productDetail =
        new ProductDetailRequest(
            25.5,
            Map.of("length", 200, "width", 85, "height", 90),
            "가죽, 목재, 폼",
            "대한민국",
            "2년 품질 보증",
            "마른 천으로 표면을 닦아주세요",
            Map.of("assembly_required", true, "assembly_time", "30분"));
    ProductPriceRequest priceRequest =
        new ProductPriceRequest(
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(39000),
            BigDecimal.valueOf(4000),
            "KRW",
            BigDecimal.valueOf(10));

    return new ProductSaveRequest(
        "",
        "super-comfortable-sofa",
        "최고급 소재로 만든 편안한 소파",
        "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
        1L,
        2L,
        "ACTIVE",
        productDetail,
        priceRequest,
        List.of(new ProductCategoryRequest(5L, true)),
        List.of(new ProductOptionGroupRequest("색상", 1, List.of())),
        List.of(),
        List.of()
    );
  }

  private ProductSaveRequest getMockProductSaveRequest(Long sellerId, Long brandId) {

    ProductDetailRequest productDetail =
        new ProductDetailRequest(
            25.5,
            Map.of("length", 200, "width", 85, "height", 90),
            "가죽, 목재, 폼",
            "대한민국",
            "2년 품질 보증",
            "마른 천으로 표면을 닦아주세요",
            Map.of("assembly_required", true, "assembly_time", "30분"));
    ProductPriceRequest priceRequest =
        new ProductPriceRequest(
            BigDecimal.valueOf(599000),
            BigDecimal.valueOf(499000),
            BigDecimal.valueOf(350000),
            "KRW",
            BigDecimal.valueOf(10));

    List<ProductCategoryRequest> categories =
        List.of(new ProductCategoryRequest(5L, true), new ProductCategoryRequest(8L, false));

    List<ProductOptionRequest> options1 =
        List.of(
            new ProductOptionRequest("브라운", BigDecimal.valueOf(0), "SOFA-BRN", 10, 1),
            new ProductOptionRequest("블랙", BigDecimal.valueOf(0), "SOFA-BLK", 15, 2));
    List<ProductOptionRequest> options2 =
        List.of(
            new ProductOptionRequest("천연 가죽", BigDecimal.valueOf(100000), "SOFA-LTHR", 5, 1),
            new ProductOptionRequest("인조 가죽", BigDecimal.valueOf(0), "SOFA-FAKE", 20, 2));

    List<ProductOptionGroupRequest> optionGroupRequests =
        List.of(
            new ProductOptionGroupRequest("색상", 1, options1),
            new ProductOptionGroupRequest("소재", 2, options2));

    List<ProductImageRequest> images =
        List.of(
            new ProductImageRequest("http://example.com/images/sofa1.jpg", "브라운 소파 정면", true, 1, null),
            new ProductImageRequest("http://example.com/images/sofa2.jpg", "브라운 소파 측면", false, 2, null));
    List<Long> tags = List.of(1L, 4L, 7L);
    return new ProductSaveRequest(
        "원티드 소파",
        "wanted-sofa",
        "최고급 소재로 만든 편안한 소파",
        "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
        sellerId,
        brandId,
        "ACTIVE",
        productDetail,
        priceRequest,
        categories,
        optionGroupRequests,
        images,
        tags);
  }
}
