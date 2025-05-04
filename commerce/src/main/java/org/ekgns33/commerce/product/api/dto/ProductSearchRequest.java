package org.ekgns33.commerce.product.api.dto;

import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ekgns33.commerce.product.service.dto.ProductSearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
public class ProductSearchRequest {

  @Min(1)
  private Integer page = 1;

  @Min(1)
  private Integer perPage = 10;

  private String sort = "created_at:desc";

  private String status;
  private Integer minPrice;
  private Integer maxPrice;
  private List<Integer> category = new ArrayList<>();
  private Integer seller;
  private Integer brand;
  private Boolean inStock = false;
  private String search = "";

  private Pageable toPageable() {
    int pageIndex = page - 1;
    return PageRequest.of(pageIndex, perPage, parseSort(sort));
  }

  private Sort parseSort(String sortString) {
    if (!StringUtils.hasText(sortString)) {
      return Sort.by(Sort.Direction.DESC, "createdAt");
    }

    return Arrays.stream(sortString.split(",")).map(this::parseOrder).toList().isEmpty()
        ? Sort.by(Sort.Direction.DESC, "createdAt")
        : Sort.by(Arrays.stream(sortString.split(",")).map(this::parseOrder).toList());
  }

  private Sort.Order parseOrder(String orderString) {
    String[] parts = orderString.split(":");
    String field = toCamelCase(parts[0]);
    Sort.Direction direction =
        parts.length > 1 && parts[1].equalsIgnoreCase("asc")
            ? Sort.Direction.ASC
            : Sort.Direction.DESC;
    return new Sort.Order(direction, field);
  }

  private String toCamelCase(String snake) {
    String[] parts = snake.split("_");
    return parts[0]
        + Arrays.stream(parts, 1, parts.length)
            .map(p -> Character.toUpperCase(p.charAt(0)) + p.substring(1))
            .collect(Collectors.joining());
  }

  public ProductSearchQuery toQuery() {
    Pageable pageable = toPageable();
    return new ProductSearchQuery(
        pageable,
        ProductStatus.valueOf(status),
        brand != null ? brand.longValue() : null,
        seller != null ? seller.longValue() : null,
        category != null && !category.isEmpty() ? category.getFirst().longValue() : null,
        minPrice,
        maxPrice,
        inStock,
        search);
  }
}
