package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ekgns33.commerce.common.audit.CreateUpdateAudit;
import org.ekgns33.commerce.product.api.dto.ProductStatus;
import org.ekgns33.commerce.product.service.dto.command.ProductUpdateCommand;

@Table(name = "products")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends CreateUpdateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true, nullable = false)
  private String slug;

  private String shortDescription;
  private String fullDescription;
  private Long sellerId;
  private Long brandId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductStatus status;

  // CREATION METHODS
  @Builder
  private Product(
      Long id,
      String name,
      String slug,
      String shortDescription,
      String fullDescription,
      Long sellerId,
      Long brandId,
      ProductStatus status) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.shortDescription = shortDescription;
    this.fullDescription = fullDescription;
    this.sellerId = sellerId;
    this.brandId = brandId;
    this.status = status;
  }

  public static Product withOutId(
      String name,
      String slug,
      String shortDescription,
      String fullDescription,
      Long sellerId,
      Long brandId,
      ProductStatus status) {
    return Product.builder()
        .name(name)
        .slug(slug)
        .shortDescription(shortDescription)
        .fullDescription(fullDescription)
        .sellerId(sellerId)
        .brandId(brandId)
        .status(status)
        .build();
  }

  public void update(ProductUpdateCommand updateCommand) {
    this.name = updateCommand.name();
    this.slug = updateCommand.slug();
    this.shortDescription = updateCommand.shortDescription();
    this.fullDescription = updateCommand.fullDescription();
    this.sellerId = updateCommand.sellerId();
    this.brandId = updateCommand.brandId();
    this.status = updateCommand.status();
  }
}
