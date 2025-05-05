package org.ekgns33.commerce.product.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.ekgns33.commerce.common.valid.EnumValid;

public record ProductUpdateRequest(
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
    List<Long> tags) {}
