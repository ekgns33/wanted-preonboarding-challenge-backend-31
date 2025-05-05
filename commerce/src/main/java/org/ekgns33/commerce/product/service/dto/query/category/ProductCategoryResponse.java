package org.ekgns33.commerce.product.service.dto.query.category;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategoryResponse {

  private Long categoryId;
  private String name;
  private String slug;
  private boolean isPrimary;
  private ParentCategoryResponse parent;

  @QueryProjection
  public ProductCategoryResponse(
      Long categoryId,
      String name,
      String slug,
      boolean isPrimary,
      Long parentId,
      String parentName,
      String parentSlug) {
    this.categoryId = categoryId;
    this.name = name;
    this.slug = slug;
    this.isPrimary = isPrimary;
    this.parent = new ParentCategoryResponse(parentId, parentName, parentSlug);
  }
}
