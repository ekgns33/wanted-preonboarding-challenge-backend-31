package org.ekgns33.commerce.product.service.dto.command;

import java.math.BigDecimal;
import java.util.Map;

public record DetailVO(
    BigDecimal weight,
    Map<String, Object> dimensions,
    String materials,
    String countryOfOrigin,
    String warrantyInfo,
    String careInstructions,
    Map<String, Object> additionalInfo) {}
