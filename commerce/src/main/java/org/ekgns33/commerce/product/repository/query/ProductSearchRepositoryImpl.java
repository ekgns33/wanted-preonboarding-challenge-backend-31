package org.ekgns33.commerce.product.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ekgns33.commerce.product.api.dto.ProductStatus;
import org.ekgns33.commerce.product.domain.QBrand;
import org.ekgns33.commerce.product.domain.QCategory;
import org.ekgns33.commerce.product.domain.QProduct;
import org.ekgns33.commerce.product.domain.QProductCategory;
import org.ekgns33.commerce.product.domain.QProductImage;
import org.ekgns33.commerce.product.domain.QProductOption;
import org.ekgns33.commerce.product.domain.QProductOptionGroup;
import org.ekgns33.commerce.product.domain.QProductPrice;
import org.ekgns33.commerce.product.domain.QReview;
import org.ekgns33.commerce.product.service.dto.QSearchedProductDto;
import org.ekgns33.commerce.product.service.dto.QSearchedProductDto_BrandDto;
import org.ekgns33.commerce.product.service.dto.QSearchedProductDto_ImageDto;
import org.ekgns33.commerce.product.service.dto.QSearchedProductDto_SellerDto;
import org.ekgns33.commerce.product.service.dto.SearchedProductDto;
import org.ekgns33.commerce.product.service.dto.query.ProductSimpleInfoDto;
import org.ekgns33.commerce.product.service.dto.query.QBrandDetailResponse;
import org.ekgns33.commerce.product.service.dto.query.QProductSimpleInfoDto;
import org.ekgns33.commerce.product.service.dto.query.QSellerDetailResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductOptionGroupResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductOptionResponse;
import org.ekgns33.commerce.product.service.dto.query.product.ProductSearchQuery;
import org.ekgns33.commerce.seller.domain.QSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepository {

  private final JPAQueryFactory queryFactory;
  private final QProduct product = QProduct.product;
  private final QProductPrice price = QProductPrice.productPrice;
  private final QBrand brand = QBrand.brand;
  private final QSeller seller = QSeller.seller;
  private final QProductOptionGroup optionGroup = QProductOptionGroup.productOptionGroup;
  private final QProductOption option = QProductOption.productOption;
  private final QProductImage image = QProductImage.productImage;
  private final QProductCategory productCategory = QProductCategory.productCategory;
  private final QCategory category = QCategory.category;
  private final QReview review = QReview.review;

  @Override
  public Page<SearchedProductDto> search(ProductSearchQuery query) {

    OrderSpecifier[] orderSpecifier = getOrderSpecifiers(query.pageable().getSort());

    // fetch productEntities
    List<SearchedProductDto> content =
        queryFactory
            .selectDistinct(
                new QSearchedProductDto(
                    product.id,
                    product.name,
                    product.slug,
                    product.shortDescription,
                    price.basePrice,
                    price.salePrice,
                    price.currency,
                    new QSearchedProductDto_ImageDto(image.url, image.altText),
                    new QSearchedProductDto_BrandDto(brand.id, brand.name),
                    new QSearchedProductDto_SellerDto(seller.id, seller.name),
                    seller.rating,
                    new CaseBuilder().when(option.stock.gt(0)).then(true).otherwise(false),
                    product.status,
                    product.createdAt))
            .from(product)
            .join(price)
            .on(price.productId.eq(product.id))
            .leftJoin(image)
            .on(image.productId.eq(product.id).and(image.isPrimary))
            .join(brand)
            .on(product.brandId.eq(brand.id))
            .leftJoin(optionGroup)
            .on(optionGroup.productId.eq(product.id))
            .leftJoin(option)
            .on(optionGroup.id.eq(option.optionGroupId))
            .join(seller)
            .on(product.sellerId.eq(seller.id))
            .leftJoin(productCategory)
            .on(productCategory.productId.eq(product.id))
            .join(category)
            .on(productCategory.categoryId.eq(category.id))
            .where(
                statusActive(),
                priceBetween(query.minPrice(), query.maxPrice()),
                brandIdEquals(query.brandId()),
                sellerIdEquals(query.sellerId()),
                categoryIdEquals(query.categoryId()),
                inStock(query.inStock()),
                searchKeywordContains(query.searchKeyword()))
            .offset(query.pageable().getOffset())
            .limit(query.pageable().getPageSize())
            .orderBy(orderSpecifier)
            .fetch();

    // fetch total count
    Long total =
        queryFactory
            .selectDistinct(product.countDistinct())
            .from(product)
            .join(price)
            .on(price.productId.eq(product.id))
            .leftJoin(image)
            .on(image.productId.eq(product.id).and(image.isPrimary))
            .join(brand)
            .on(product.brandId.eq(brand.id))
            .leftJoin(optionGroup)
            .on(optionGroup.productId.eq(product.id))
            .leftJoin(option)
            .on(optionGroup.id.eq(option.optionGroupId))
            .join(seller)
            .on(product.sellerId.eq(seller.id))
            .leftJoin(productCategory)
            .on(productCategory.productId.eq(product.id))
            .join(category)
            .on(productCategory.categoryId.eq(category.id))
            .where(
                statusActive(),
                priceBetween(query.minPrice(), query.maxPrice()),
                brandIdEquals(query.brandId()),
                sellerIdEquals(query.sellerId()),
                categoryIdEquals(query.categoryId()),
                inStock(query.inStock()),
                searchKeywordContains(query.searchKeyword()))
            .fetchOne();
    return new PageImpl<>(content, query.pageable(), total);
  }

  @Override
  public Optional<ProductSimpleInfoDto> findSimpleInfoById(Long id) {

    return Optional.ofNullable(
        queryFactory
            .select(
                new QProductSimpleInfoDto(
                    product.id,
                    product.name,
                    product.slug,
                    product.shortDescription,
                    product.fullDescription,
                    new QSellerDetailResponse(
                        seller.id,
                        seller.name,
                        seller.description,
                        seller.logoUrl,
                        seller.rating,
                        seller.contactEmail,
                        seller.contactPhone),
                    new QBrandDetailResponse(
                        brand.id, brand.name, brand.description, brand.logoUrl, brand.website),
                    product.status,
                    product.createdAt,
                    product.updatedAt))
            .from(product)
            .join(brand)
            .on(product.brandId.eq(brand.id))
            .join(seller)
            .on(product.sellerId.eq(seller.id))
            .where(product.id.eq(id))
            .fetchOne());
  }

  public List<ProductOptionGroupResponse> findProductOptionGroupDtoByProductId(Long productId) {
    List<Tuple> results =
        queryFactory
            .select(
                optionGroup.id,
                optionGroup.name,
                optionGroup.displayOrder,
                option.id,
                option.name,
                option.additionalPrice,
                option.sku,
                option.stock,
                option.displayOrder)
            .from(optionGroup)
            .leftJoin(option)
            .on(option.optionGroupId.eq(optionGroup.id))
            .where(optionGroup.productId.eq(productId))
            .fetch();

    Map<Long, ProductOptionGroupResponse> groupMap = new LinkedHashMap<>();

    for (Tuple tuple : results) {
      Long groupId = tuple.get(optionGroup.id);
      if (!groupMap.containsKey(groupId)) {
        ProductOptionGroupResponse groupDto =
            new ProductOptionGroupResponse(
                groupId,
                tuple.get(optionGroup.name),
                tuple.get(optionGroup.displayOrder),
                new ArrayList<>());
        groupMap.put(groupId, groupDto);
      }

      Long optionId = tuple.get(option.id);
      if (optionId != null) {
        ProductOptionResponse optionDto =
            new ProductOptionResponse(
                optionId,
                tuple.get(option.name),
                tuple.get(option.additionalPrice),
                tuple.get(option.sku),
                tuple.get(option.stock),
                tuple.get(option.displayOrder));
        groupMap.get(groupId).getOptions().add(optionDto);
      }
    }

    return new ArrayList<>(groupMap.values());
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
    List<OrderSpecifier<?>> orders = new ArrayList<>();

    QProduct product = QProduct.product;
    QProductPrice price = QProductPrice.productPrice;
    QBrand brand = QBrand.brand;

    Map<String, Path<?>> pathMap =
        Map.of(
            "id", product.id,
            "name", product.name,
            "createdAt", product.createdAt,
            "basePrice", price.basePrice,
            "salePrice", price.salePrice,
            "brandName", brand.name);

    for (Sort.Order order : sort) {
      String property = order.getProperty();
      Path<?> path = pathMap.get(property);
      if (path == null) {
        continue;
      }
      OrderSpecifier<?> orderSpecifier = createOrderSpecifier(path, order.getDirection());
      orders.add(orderSpecifier);
    }

    if (orders.isEmpty()) {
      orders.add(product.createdAt.desc());
    }

    return orders.toArray(new OrderSpecifier[0]);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private <T extends Comparable> OrderSpecifier<?> createOrderSpecifier(
      Path<?> path, Direction direction) {
    Order order = direction.isAscending() ? Order.ASC : Order.DESC;
    OrderSpecifier.NullHandling nullHandling = OrderSpecifier.NullHandling.NullsLast;
    return new OrderSpecifier(order, path, nullHandling);
  }

  private BooleanExpression statusActive() {
    return QProduct.product.status.eq(ProductStatus.ACTIVE);
  }

  private BooleanExpression imagePrimary() {
    return QProductImage.productImage.isPrimary;
  }

  private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
    if (minPrice != null && maxPrice != null) {
      return price.basePrice.between(minPrice, maxPrice);
    } else if (minPrice != null) {
      return price.basePrice.goe(minPrice);
    } else if (maxPrice != null) {
      return price.basePrice.loe(maxPrice);
    } else {
      return null;
    }
  }

  private BooleanExpression brandIdEquals(Long brandId) {
    if (brandId == null) {
      return null;
    }
    return QBrand.brand.id.eq(brandId);
  }

  private BooleanExpression sellerIdEquals(Long sellerId) {
    if (sellerId == null) {
      return null;
    }
    return QSeller.seller.id.eq(sellerId);
  }

  private BooleanExpression categoryIdEquals(Long categoryId) {
    if (categoryId == null) {
      return null;
    }
    return QProductCategory.productCategory.id.eq(categoryId);
  }

  private BooleanExpression inStock(Boolean inStock) {
    if (Boolean.TRUE.equals(inStock)) {
      return QProductOption.productOption.stock.gt(0);
    }
    return null;
  }

  private BooleanExpression searchKeywordContains(String keyword) {
    if (!StringUtils.hasText(keyword)) {
      return null;
    }

    QProduct product = QProduct.product;
    QBrand brand = QBrand.brand;
    QCategory category = QCategory.category;

    return product
        .name
        .containsIgnoreCase(keyword)
        .or(product.shortDescription.containsIgnoreCase(keyword))
        .or(product.fullDescription.containsIgnoreCase(keyword))
        .or(brand.name.containsIgnoreCase(keyword))
        .or(category.name.containsIgnoreCase(keyword));
  }
}
