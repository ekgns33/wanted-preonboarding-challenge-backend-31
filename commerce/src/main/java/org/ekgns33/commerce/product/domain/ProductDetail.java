package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Table(name = "product_details")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "weight")
  private Double weight;

  @Column(name = "dimensions")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> dimensions = new HashMap<>();

  @Column(name = "materials")
  private String materials;

  @Column(name = "country_of_origin")
  private String countryOfOrigin;

  @Column(name = "warranty_info")
  private String warrantyInfo;

  @Column(name = "care_instructions")
  private String careInstructions;

  @Column(name = "additional_info")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> additionalInfo;

  @Builder
  public ProductDetail(
      Long id,
      Long productId,
      Double weight,
      Map<String, Object> dimensions,
      String materials,
      String countryOfOrigin,
      String warrantyInfo,
      String careInstructions,
      Map<String, Object> additionalInfo) {
    this.id = id;
    this.productId = productId;
    this.weight = weight;
    this.dimensions = dimensions;
    this.materials = materials;
    this.countryOfOrigin = countryOfOrigin;
    this.warrantyInfo = warrantyInfo;
    this.careInstructions = careInstructions;
    this.additionalInfo = additionalInfo;
  }

  public static ProductDetail withOutId(
      Long productId,
      Double weight,
      Map<String, Object> dimensions,
      String materials,
      String countryOfOrigin,
      String warrantyInfo,
      String careInstructions,
      Map<String, Object> additionalInfo) {
    return ProductDetail.builder()
        .productId(productId)
        .weight(weight)
        .dimensions(dimensions)
        .materials(materials)
        .countryOfOrigin(countryOfOrigin)
        .warrantyInfo(warrantyInfo)
        .careInstructions(careInstructions)
        .additionalInfo(additionalInfo)
        .build();
  }
}
