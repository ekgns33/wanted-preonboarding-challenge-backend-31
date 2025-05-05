package org.ekgns33.commerce.product.api.dto;

import java.math.BigDecimal;

public record ProductOptionRequest(
    String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {}
