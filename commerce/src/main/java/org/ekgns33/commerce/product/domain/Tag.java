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

@Table(name = "tags")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "slug", unique = true, nullable = false)
  private String slug;

  @Builder
  private Tag(Long id, String name, String slug) {
    this.id = id;
    this.name = name;
    this.slug = slug;
  }

  public static Tag withOutId(String name, String slug) {
    return Tag.builder()
        .name(name)
        .slug(slug)
        .build();
  }
}
