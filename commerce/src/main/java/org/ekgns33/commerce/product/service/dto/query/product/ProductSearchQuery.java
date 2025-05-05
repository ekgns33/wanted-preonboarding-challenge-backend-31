package org.ekgns33.commerce.product.service.dto.query.product;

import org.ekgns33.commerce.product.api.dto.ProductStatus;
import org.springframework.data.domain.Pageable;

public record ProductSearchQuery(
    Pageable pageable,
    ProductStatus status,
    Long brandId,
    Long sellerId,
    Long categoryId,
    Integer minPrice,
    Integer maxPrice,
    Boolean inStock,
    String searchKeyword) {}
