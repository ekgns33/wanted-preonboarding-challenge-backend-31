package org.ekgns33.commerce.product.repository.query;

import org.ekgns33.commerce.product.service.dto.query.product.ProductRatingResponse;

public interface ReviewQueryRepository {

  ProductRatingResponse findProductRatingWithReviewByProductId(Long id);
}
