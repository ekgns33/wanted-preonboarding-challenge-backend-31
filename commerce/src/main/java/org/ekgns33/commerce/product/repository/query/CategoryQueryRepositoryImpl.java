package org.ekgns33.commerce.product.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ekgns33.commerce.product.domain.QCategory;
import org.ekgns33.commerce.product.domain.QProductCategory;
import org.ekgns33.commerce.product.service.dto.query.category.ProductCategoryResponse;
import org.ekgns33.commerce.product.service.dto.query.category.QProductCategoryResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ProductCategoryResponse> findProductCategoryDtoByProductId(Long id) {
    QProductCategory pc = QProductCategory.productCategory;
    QCategory childCategory = new QCategory("childCategory");
    QCategory parentCategory = new QCategory("parentCategory");

    return queryFactory
        .select(
            new QProductCategoryResponse(
                childCategory.id,
                childCategory.name,
                childCategory.slug,
                pc.isPrimary,
                parentCategory.id,
                parentCategory.name,
                parentCategory.slug))
        .from(pc)
        .join(childCategory)
        .on(pc.categoryId.eq(childCategory.id))
        .leftJoin(parentCategory)
        .on(childCategory.parent.id.eq(parentCategory.id))
        .where(pc.productId.eq(id))
        .fetch();
  }
}
