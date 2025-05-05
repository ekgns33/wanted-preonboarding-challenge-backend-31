package org.ekgns33.commerce.product.service.dto.command;

import java.util.List;
import org.ekgns33.commerce.product.api.dto.ProductStatus;

public record ProductCreateCommand(
    String name,
    String slug,
    String shortDescription,
    String fullDescription,
    Long sellerId,
    Long brandId,
    ProductStatus status,
    DetailVO detailVO,
    PriceVO priceVO,
    List<ProductCategoryVO> categories,
    List<OptionGroupVO> optionGroups,
    List<ImageVO> imageVOS,
    List<Long> tags) {}
