package org.ekgns33.commerce.product.service.dto;

import org.ekgns33.commerce.product.domain.Product;
import org.ekgns33.commerce.product.domain.ProductDetail;
import org.ekgns33.commerce.product.domain.ProductImage;
import org.ekgns33.commerce.product.domain.ProductOption;
import org.ekgns33.commerce.product.domain.ProductOptionGroup;
import org.ekgns33.commerce.product.domain.ProductPrice;
import org.ekgns33.commerce.product.domain.ProductTag;
import org.ekgns33.commerce.product.service.dto.command.DetailVO;
import org.ekgns33.commerce.product.service.dto.command.ImageVO;
import org.ekgns33.commerce.product.service.dto.command.OptionGroupVO;
import org.ekgns33.commerce.product.service.dto.command.OptionVO;
import org.ekgns33.commerce.product.service.dto.command.PriceVO;
import org.ekgns33.commerce.product.service.dto.command.ProductCategoryVO;
import org.ekgns33.commerce.product.service.dto.command.ProductCreateCommand;

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

  public static ProductDetail mapToProductDetail(Long productId, DetailVO detailVO) {
    return ProductDetail.withOutId(
        productId,
        detailVO.weight(),
        detailVO.dimensions(),
        detailVO.materials(),
        detailVO.countryOfOrigin(),
        detailVO.warrantyInfo(),
        detailVO.careInstructions(),
        detailVO.additionalInfo());
  }

  public static ProductPrice mapToProductPrice(Long productId, PriceVO priceVO) {
    return ProductPrice.withOutId(
        productId,
        priceVO.basePrice(),
        priceVO.salePrice(),
        priceVO.costPrice(),
        priceVO.currency(),
        priceVO.taxRate());
  }

  public static org.ekgns33.commerce.product.domain.ProductCategory mapToCategory(Long productId, ProductCategoryVO productCategoryVO) {
    return org.ekgns33.commerce.product.domain.ProductCategory.withOutId(productId, productCategoryVO.categoryId(), productCategoryVO.isPrimary());
  }

  public static ProductOptionGroup mapToOptionGroup(Long productId, OptionGroupVO optionGroupVO) {
    return ProductOptionGroup.withOutId(productId, optionGroupVO.name(), optionGroupVO.displayOrder());
  }

  public static ProductOption mapToOption(Long optionGroupId, OptionVO optionVO) {
    return ProductOption.withOutId(
        optionGroupId,
        optionVO.name(),
        optionVO.additionalPrice(),
        optionVO.sku(),
        optionVO.stock(),
        optionVO.displayOrder());
  }

  public static ProductImage mapToImage(Long productId, ImageVO imageVO) {
    return ProductImage.withOutId(
        productId,
        imageVO.optionId(),
        imageVO.url(),
        imageVO.altText(),
        imageVO.isPrimary(),
        imageVO.displayOrder());
  }

  public static ProductTag mapToTag(Long productId, Long tagId) {
    return ProductTag.withOutId(productId, tagId);
  }
}
