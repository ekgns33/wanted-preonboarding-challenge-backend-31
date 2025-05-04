package org.ekgns33.commerce.product.service.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.ekgns33.commerce.product.api.dto.ProductStatus;
import org.ekgns33.commerce.product.domain.Currency;

public record ProductCreateCommand(
    String name,
    String slug,
    String shortDescription,
    String fullDescription,
    Long sellerId,
    Long brandId,
    ProductStatus status,
    Detail detail,
    Price price,
    List<Category> categories,
    List<OptionGroup> optionGroups,
    List<Image> images,
    List<Long> tags) {
  public record Detail(
      BigDecimal weight,
      Map<String, Object> dimensions,
      String materials,
      String countryOfOrigin,
      String warrantyInfo,
      String careInstructions,
      Map<String, Object> additionalInfo) {}

  public record Price(
      BigDecimal basePrice,
      BigDecimal salePrice,
      BigDecimal costPrice,
      Currency currency,
      BigDecimal taxRate) {}

  public record Category(Long categoryId, boolean isPrimary) {}

  public record OptionGroup(String name, Integer displayOrder, List<Option> options) {}

  public record Option(
      String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {}

  public record Image(
      String url, String altText, boolean isPrimary, Integer displayOrder, Long optionId) {}
}
