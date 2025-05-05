package org.ekgns33.commerce.product.api.dto;

import java.util.Map;

public record ProductDetailRequest(
    Double weight,
    Map<String, Object> dimensions,
    String materials,
    String countryOfOrigin,
    String warrantyInfo,
    String careInstructions,
    Map<String, Object> additionalInfo) {}
