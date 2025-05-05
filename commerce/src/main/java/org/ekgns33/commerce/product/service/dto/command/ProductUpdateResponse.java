package org.ekgns33.commerce.product.service.dto.command;

import java.time.LocalDateTime;
import org.ekgns33.commerce.product.domain.Product;

public record ProductUpdateResponse(
    Long id, String name, String slug, LocalDateTime createdAt, LocalDateTime updatedAt) {
  public static ProductUpdateResponse of(Product product) {
    return new ProductUpdateResponse(
        product.getId(),
        product.getName(),
        product.getSlug(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }
}
