package org.ekgns33.commerce.common;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TruncateDatabaseCleaner {

  @PersistenceContext private EntityManager em;

  private static final List<String> PRODUCT_TABLES = new ArrayList<>();

  @PostConstruct
  public void init() {
    extractTableNames().stream().filter(e -> e.startsWith("product")).forEach(PRODUCT_TABLES::add);
  }

  public List<String> extractTableNames() {
    Set<EntityType<?>> entities = em.getMetamodel().getEntities();

    return entities.stream()
        .map(EntityType::getJavaType)
        .map(this::resolveTableName)
        .collect(Collectors.toList());
  }

  private String resolveTableName(Class<?> clazz) {
    Table table = clazz.getAnnotation(Table.class);
    if (table != null && !table.name().isEmpty()) {
      return table.name();
    } else {
      return clazz.getSimpleName();
    }
  }

  @Transactional
  public void truncateProductRelatedTables() {
    em.flush();
    em.clear();
    em.createNativeQuery(buildTruncateQuery()).executeUpdate();
  }

  private String buildTruncateQuery() {
    return "TRUNCATE TABLE " + String.join(", ", PRODUCT_TABLES) + " RESTART IDENTITY CASCADE";
  }
}
