package org.ekgns33.commerce.product.api.dto;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record ProductPriceRequest(
    @Min(message = "기본 가격은 0보다 커야 합니다.", value = 1) BigDecimal basePrice,
    @Min(message = "가격은 0보다 커야 합니다.", value = 1) BigDecimal salePrice,
    @Min(message = "가격은 0보다 커야 합니다.", value = 1) BigDecimal costPrice,
    String currency,
    BigDecimal taxRate) {}
