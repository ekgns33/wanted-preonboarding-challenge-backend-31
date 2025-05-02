package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "brands")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true, nullable = false)
  private String slug;

  private String description;

  @Column(name = "logo_url")
  private String logoUrl;

  private String website;

  @Builder
  private Brand(
      Long id, String name, String slug, String description, String logoUrl, String website) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.description = description;
    this.logoUrl = logoUrl;
    this.website = website;
  }
}
