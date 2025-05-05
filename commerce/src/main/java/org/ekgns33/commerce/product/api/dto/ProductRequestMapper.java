package org.ekgns33.commerce.product.api.dto;

import org.ekgns33.commerce.product.domain.Currency;
import org.ekgns33.commerce.product.service.dto.command.DetailVO;
import org.ekgns33.commerce.product.service.dto.command.ImageVO;
import org.ekgns33.commerce.product.service.dto.command.OptionGroupVO;
import org.ekgns33.commerce.product.service.dto.command.OptionVO;
import org.ekgns33.commerce.product.service.dto.command.PriceVO;
import org.ekgns33.commerce.product.service.dto.command.ProductCategoryVO;
import org.ekgns33.commerce.product.service.dto.command.ProductCreateCommand;
import org.ekgns33.commerce.product.service.dto.command.ProductUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

  ProductCreateCommand toCommand(ProductSaveRequest dto);

  ProductUpdateCommand toCommand(ProductUpdateRequest dto);

  DetailVO toDetailVO(ProductDetailRequest dto);

  PriceVO toPriceVO(ProductPriceRequest dto);

  ProductCategoryVO toProductCategoryVO(ProductCategoryRequest dto);

  ImageVO toImageVO(ProductImageRequest dto);

  @Mapping(target = "optionVOS", source = "options")
  OptionGroupVO toOptionGroupVO(ProductOptionGroupRequest dto);

  OptionVO toOptionVO(ProductOptionRequest dto);

  default ProductStatus mapStatus(String status) {
    return status == null ? null : ProductStatus.valueOf(status);
  }

  default Currency mapCurrency(String currency) {
    return currency == null ? null : Currency.valueOf(currency);
  }
}
