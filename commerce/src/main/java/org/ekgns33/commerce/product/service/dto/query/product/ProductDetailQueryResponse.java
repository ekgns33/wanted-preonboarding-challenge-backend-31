package org.ekgns33.commerce.product.service.dto.query.product;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ekgns33.commerce.product.api.dto.ProductStatus;
import org.ekgns33.commerce.product.service.dto.query.BrandDetailResponse;
import org.ekgns33.commerce.product.service.dto.query.ProductSimpleInfoDto;
import org.ekgns33.commerce.product.service.dto.query.SellerDetailResponse;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailQueryResponse {

  private Long id;
  private String name;
  private String slug;
  private String shortDescription;
  private String fullDescription;
  private SellerDetailResponse seller;
  private BrandDetailResponse brand;
  private ProductStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private ProductDetailResponse detail;
  private ProductPriceResponse price;
  private List<ProductCategoryResponse> categories;
  private List<ProductOptionGroupResponse> optionGroups;
  private List<ProductImageResponse> images;
  private List<ProductTagResponse> tags;
  private ProductRatingResponse rating;
  private List<ProductRelatedResponse> relatedProducts;

  public ProductDetailQueryResponse(
      ProductSimpleInfoDto productSimpleInfoDto,
      ProductDetailResponse detail,
      ProductPriceResponse price,
      List<ProductCategoryResponse> categories,
      List<ProductOptionGroupResponse> optionGroups,
      List<ProductImageResponse> images,
      List<ProductTagResponse> tags,
      ProductRatingResponse rating,
      List<ProductRelatedResponse> relatedProducts) {
    this.id = productSimpleInfoDto.getId();
    this.name = productSimpleInfoDto.getName();
    this.slug = productSimpleInfoDto.getSlug();
    this.shortDescription = productSimpleInfoDto.getShortDescription();
    this.fullDescription = productSimpleInfoDto.getFullDescription();
    this.seller = productSimpleInfoDto.getSeller();
    this.brand = productSimpleInfoDto.getBrand();
    this.status = productSimpleInfoDto.getStatus();
    this.createdAt = productSimpleInfoDto.getCreatedAt();
    this.updatedAt = productSimpleInfoDto.getUpdatedAt();
    this.detail = detail;
    this.price = price;
    this.categories = categories;
    this.optionGroups = optionGroups;
    this.images = images;
    this.tags = tags;
    this.rating = rating;
    this.relatedProducts = relatedProducts;
  }

}
