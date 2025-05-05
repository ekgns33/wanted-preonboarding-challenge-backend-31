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
import org.ekgns33.commerce.product.service.dto.command.OptionGroupVO;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "product_option_groups")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id")
  private Long productId;

  @Column(nullable = false)
  private String name;

  @ColumnDefault(value = "0")
  private Integer displayOrder;

  @Builder
  private ProductOptionGroup(Long id, Long productId, String name, Integer displayOrder) {
    this.id = id;
    this.productId = productId;
    this.name = name;
    this.displayOrder = displayOrder;
  }

  public static ProductOptionGroup withOutId(Long productId, String name, Integer displayOrder) {
    return ProductOptionGroup.builder()
        .productId(productId)
        .name(name)
        .displayOrder(displayOrder)
        .build();
  }

  public void update(OptionGroupVO optionGroupVO) {
    this.displayOrder = optionGroupVO.displayOrder();
  }
}
