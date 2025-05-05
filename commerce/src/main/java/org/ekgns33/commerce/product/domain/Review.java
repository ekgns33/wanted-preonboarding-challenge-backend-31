package org.ekgns33.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ekgns33.commerce.common.audit.CreateUpdateAudit;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "reviews")
@Entity
@Getter
@NoArgsConstructor
public class Review extends CreateUpdateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "product_id")
  private Long productId;
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "rating", nullable = false)
  @Min(value = 1)
  @Max(value = 5)
  private Integer rating;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "verified_purchase")
  @ColumnDefault(value = "false")
  private Boolean verifiedPurchase;

  @Column(name = "helpful_votes")
  @ColumnDefault(value = "0")
  private Integer helpfulVotes;

  @Builder
  private Review(Long id, Long productId, Long userId, Integer rating, String title, String content,
      Boolean verifiedPurchase, Integer helpfulVotes) {
    this.id = id;
    this.productId = productId;
    this.userId = userId;
    this.rating = rating;
    this.title = title;
    this.content = content;
    this.verifiedPurchase = verifiedPurchase;
    this.helpfulVotes = helpfulVotes;
  }

  public static Review withOutId(
      Long productId,
      Long userId,
      Integer rating,
      String title,
      String content,
      Boolean verifiedPurchase,
      Integer helpfulVotes) {
    return Review.builder()
        .productId(productId)
        .userId(userId)
        .rating(rating)
        .title(title)
        .content(content)
        .verifiedPurchase(verifiedPurchase)
        .helpfulVotes(helpfulVotes)
        .build();
  }
}
