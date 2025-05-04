package org.ekgns33.commerce.product.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ekgns33.commerce.product.api.dto.ProductStatus;
import org.ekgns33.commerce.product.domain.Currency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedProductDto {
  private Long id;
  private String name;
  private String slug;
  private String shortDescription;
  private BigDecimal basePrice;
  private BigDecimal salePrice;
  private Currency currency;
  private ImageDto primaryImage;
  private BrandDto brand;
  private SellerDto seller;
  private BigDecimal rating;
  private Integer reviewCount;
  private Boolean inStock;
  private ProductStatus status;
  private LocalDateTime createdAt;

  @QueryProjection
  public SearchedProductDto(
      Long id,
      String name,
      String slug,
      String shortDescription,
      BigDecimal basePrice,
      BigDecimal salePrice,
      Currency currency,
      ImageDto primaryImage,
      BrandDto brand,
      SellerDto seller,
      BigDecimal rating,
      Boolean inStock,
      ProductStatus status,
      LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.shortDescription = shortDescription;
    this.basePrice = basePrice;
    this.salePrice = salePrice;
    this.currency = currency;
    this.primaryImage = primaryImage;
    this.brand = brand;
    this.seller = seller;
    this.rating = rating;
    this.inStock = inStock;
    this.status = status;
    this.createdAt = createdAt;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Builder
  public static class BrandDto {
    private Long id;
    private String name;

    @QueryProjection
    public BrandDto(Long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Builder
  public static class SellerDto {
    private Long id;
    private String name;

    @QueryProjection
    public SellerDto(Long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Builder
  public static class ImageDto {
    private String url;
    private String altText;

    @QueryProjection
    public ImageDto(String url, String altText) {
      this.url = url;
      this.altText = altText;
    }
  }
}
