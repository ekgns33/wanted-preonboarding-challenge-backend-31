package org.ekgns33.commerce.product.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ekgns33.commerce.product.domain.QReview;
import org.ekgns33.commerce.product.service.dto.query.product.ProductRatingResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

  private final JPAQueryFactory queryFactory;
  private final QReview review = QReview.review;

  @Override
  public ProductRatingResponse findProductRatingWithReviewByProductId(Long productId) {

    Tuple result = queryFactory
        .select(
            review.rating.avg(),
            review.rating.count()
        )
        .from(review)
        .where(review.productId.eq(productId))
        .fetchOne();

    BigDecimal average = Optional.ofNullable(result.get(review.rating.avg()))
        .map(BigDecimal::valueOf)
        .orElse(BigDecimal.ZERO);

    Long count = Optional.ofNullable(result.get(review.rating.count())).orElse(0L);


    Map<Integer, Long> rawDistribution = queryFactory
        .select(review.rating, review.count())
        .from(review)
        .where(review.productId.eq(productId))
        .groupBy(review.rating)
        .fetch()
        .stream()
        .collect(Collectors.toMap(
            tuple -> tuple.get(review.rating),
            tuple -> tuple.get(review.count())
        ));

    Map<String, Object> distribution = new HashMap<>();
    for (int i = 1; i <= 5; i++) {
      distribution.put(String.valueOf(i), rawDistribution.getOrDefault(i, 0L));
    }
    return new ProductRatingResponse(
        average,
        count,
        distribution
    );
  }
}
