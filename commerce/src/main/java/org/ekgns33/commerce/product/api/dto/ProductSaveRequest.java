package org.ekgns33.commerce.product.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.ekgns33.commerce.common.valid.EnumValid;

public record ProductSaveRequest(
    @NotEmpty(message = "상품명은 필수 항목입니다.") String name,
    @NotEmpty(message = "슬러그는 필수 항목입니다.") String slug,
    String shortDescription,
    String fullDescription,
    @NotNull(message = "판매자 ID는 필수 항목입니다.") Long sellerId,
    @NotNull(message = "브랜드 ID는 필수 항목입니다.") Long brandId,
    @EnumValid(message = "상품 상태가 유효하지 않습니다.", enumClass = ProductStatus.class) String status,
    ProductDetailRequest detail,
    @Valid ProductPriceRequest price,
    List<ProductCategoryRequest> categories,
    List<ProductOptionGroupRequest> optionGroups,
    List<ProductImageRequest> images,
    List<Long> tags) {

  public record ProductDetailRequest(
      Double weight,
      Map<String, Object> dimensions,
      String materials,
      String countryOfOrigin,
      String warrantyInfo,
      String careInstructions,
      Map<String, Object> additionalInfo) {}

  public record ProductPriceRequest(
      @Min(message = "기본 가격은 0보다 커야 합니다.", value = 1) BigDecimal basePrice,
      @Min(message = "가격은 0보다 커야 합니다.", value = 1) BigDecimal salePrice,
      @Min(message = "가격은 0보다 커야 합니다.", value = 1) BigDecimal costPrice,
      String currency,
      BigDecimal taxRate) {}

  public record ProductCategoryRequest(Long categoryId, boolean isPrimary) {}

  public record ProductOptionGroupRequest(
      String name, Integer displayOrder, List<ProductOptionRequest> options) {}

  public record ProductOptionRequest(
      String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {}

  public record ProductImageRequest(
      String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {}
}
