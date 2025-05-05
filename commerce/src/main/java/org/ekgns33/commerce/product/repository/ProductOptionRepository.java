package org.ekgns33.commerce.product.repository;

import java.util.List;
import java.util.Set;
import org.ekgns33.commerce.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

  List<ProductOption> findAllByOptionGroupIdIn(Set<Long> optionGroupIdsToDelete);
}
