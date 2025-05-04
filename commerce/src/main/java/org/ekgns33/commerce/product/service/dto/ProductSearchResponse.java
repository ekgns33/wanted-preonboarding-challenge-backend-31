package org.ekgns33.commerce.product.service.dto;

import java.util.List;

public record ProductSearchResponse(
    List<SearchedProductDto> products,
    Long totalElements,
    Integer totalPages,
    Integer pageNumber,
    Integer pageSize) {}
