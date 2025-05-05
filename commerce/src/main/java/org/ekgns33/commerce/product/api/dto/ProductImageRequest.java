package org.ekgns33.commerce.product.api.dto;

public record ProductImageRequest(
    String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {}
