package org.ekgns33.commerce.product.service.dto.command;

import java.math.BigDecimal;

public record OptionVO(
    String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {}
