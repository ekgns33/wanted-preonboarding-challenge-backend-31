package org.ekgns33.commerce.product.service.dto;

import org.ekgns33.commerce.product.domain.Product;
import org.ekgns33.commerce.product.domain.ProductCategory;
import org.ekgns33.commerce.product.domain.ProductDetail;
import org.ekgns33.commerce.product.domain.ProductImage;
import org.ekgns33.commerce.product.domain.ProductOption;
import org.ekgns33.commerce.product.domain.ProductOptionGroup;
import org.ekgns33.commerce.product.domain.ProductPrice;
import org.ekgns33.commerce.product.domain.ProductTag;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.Category;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.Detail;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.Image;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.Option;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.OptionGroup;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand.Price;

public final class ProductMapper {

  public static Product mapToProduct(ProductCreateCommand command) {
    return Product.withOutId(
        command.name(),
        command.slug(),
        command.shortDescription(),
        command.fullDescription(),
        command.sellerId(),
        command.brandId(),
        command.status());
  }

  public static ProductDetail mapToProductDetail(Long productId, Detail detail) {
    return ProductDetail.withOutId(
        productId,
        detail.weight(),
        detail.dimensions(),
        detail.materials(),
        detail.countryOfOrigin(),
        detail.warrantyInfo(),
        detail.careInstructions(),
        detail.additionalInfo());
  }

  public static ProductPrice mapToProductPrice(Long productId, Price price) {
    return ProductPrice.withOutId(
        productId,
        price.basePrice(),
        price.salePrice(),
        price.costPrice(),
        price.currency(),
        price.taxRate());
  }

  public static ProductCategory mapToCategory(Long productId, Category category) {
    return ProductCategory.withOutId(productId, category.categoryId(), category.isPrimary());
  }

  public static ProductOptionGroup mapToOptionGroup(Long productId, OptionGroup optionGroup) {
    return ProductOptionGroup.withOutId(productId, optionGroup.name(), optionGroup.displayOrder());
  }

  public static ProductOption mapToOption(Long optionGroupId, Option option) {
    return ProductOption.withOutId(
        optionGroupId,
        option.name(),
        option.additionalPrice(),
        option.sku(),
        option.stock(),
        option.displayOrder());
  }

  public static ProductImage mapToImage(Long productId, Image image) {
    return ProductImage.withOutId(
        productId,
        image.optionId(),
        image.url(),
        image.altText(),
        image.isPrimary(),
        image.displayOrder());
  }

  public static ProductTag mapToTag(Long productId, Long tagId) {
    return ProductTag.withOutId(productId, tagId);
  }
}
