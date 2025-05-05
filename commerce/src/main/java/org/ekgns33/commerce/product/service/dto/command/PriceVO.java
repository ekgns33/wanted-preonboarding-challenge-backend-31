package org.ekgns33.commerce.product.service.dto.command;

import java.math.BigDecimal;
import org.ekgns33.commerce.product.domain.Currency;

public record PriceVO(
    BigDecimal basePrice,
    BigDecimal salePrice,
    BigDecimal costPrice,
    Currency currency,
    BigDecimal taxRate) {}
