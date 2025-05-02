package org.ekgns33.commerce.product.service.dto;

import java.time.LocalDateTime;
import org.ekgns33.commerce.product.domain.Product;

public record ProductSaveResponse(
    Long id, String name, String slug, LocalDateTime createdAt, LocalDateTime updatedAt) {
  public static ProductSaveResponse of(Product product) {
    return new ProductSaveResponse(
        product.getId(),
        product.getName(),
        product.getSlug(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }
}
