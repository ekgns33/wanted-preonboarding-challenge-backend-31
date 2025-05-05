package org.ekgns33.commerce.product.service.dto.command;

public record ImageVO(
    String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {}
