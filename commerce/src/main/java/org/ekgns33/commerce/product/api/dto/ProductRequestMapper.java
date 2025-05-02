package org.ekgns33.commerce.product.api.dto;

import org.ekgns33.commerce.product.domain.Currency;
import org.ekgns33.commerce.product.service.dto.ProductCreateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

  ProductCreateCommand toCommand(ProductSaveRequest dto);

  ProductCreateCommand.Detail toDetail(ProductSaveRequest.ProductDetailRequest dto);

  ProductCreateCommand.Price toPrice(ProductSaveRequest.ProductPriceRequest dto);

  ProductCreateCommand.Category toCategory(ProductSaveRequest.ProductCategoryRequest dto);

  ProductCreateCommand.Image toImage(ProductSaveRequest.ProductImageRequest dto);

  @Mapping(target = "options", source = "options")
  ProductCreateCommand.OptionGroup toOptionGroup(ProductSaveRequest.ProductOptionGroupRequest dto);

  ProductCreateCommand.Option toOption(ProductSaveRequest.ProductOptionRequest dto);

  default ProductStatus mapStatus(String status) {
    return ProductStatus.valueOf(status);
  }

  default Currency mapCurrency(String currency) {
    return Currency.valueOf(currency);
  }
}
