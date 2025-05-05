package org.ekgns33.commerce.product.service.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ekgns33.commerce.product.api.dto.ProductStatus;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSimpleInfoDto {
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

  @QueryProjection
  public ProductSimpleInfoDto(Long id, String name, String slug, String shortDescription,
      String fullDescription, SellerDetailResponse seller, BrandDetailResponse brand, ProductStatus status,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.shortDescription = shortDescription;
    this.fullDescription = fullDescription;
    this.seller = seller;
    this.brand = brand;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
