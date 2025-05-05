package org.ekgns33.commerce.product.service;

import java.util.*;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public final class EntityUpdateHelper {

  public <ENTITY, DTO, KEY> void synchronizeCollection(
      Collection<ENTITY> existingEntities,
      Collection<DTO> incomingDtos,
      Function<ENTITY, KEY> entityKeyExtractor,
      Function<DTO, KEY> dtoKeyExtractor,
      Function<DTO, ENTITY> entityCreator,
      BiConsumer<ENTITY, DTO> entityUpdater,
      Consumer<Collection<ENTITY>> deleteAllAction, // Consumer 사용
      Consumer<Collection<ENTITY>> saveAllAction) { // Consumer 사용

    if (incomingDtos == null) {
      incomingDtos = List.of();
    }
    if (existingEntities == null) {
      existingEntities = List.of();
    }

    Map<KEY, ENTITY> existingEntityMap =
        existingEntities.stream()
            .collect(Collectors.toMap(entityKeyExtractor, Function.identity()));

    Map<KEY, DTO> incomingDtoMap =
        incomingDtos.stream()
            .collect(
                Collectors.toMap(
                    dtoKeyExtractor,
                    Function.identity(),
                    (dto1, dto2) -> dto2)); // 중복 키 경우 마지막 DTO 사용

    List<ENTITY> toDelete =
        existingEntities.stream()
            .filter(entity -> !incomingDtoMap.containsKey(entityKeyExtractor.apply(entity)))
            .toList();
    if (!toDelete.isEmpty()) {
      deleteAllAction.accept(toDelete);
    }

    List<ENTITY> toSave = new ArrayList<>();
    incomingDtos.forEach(
        dto -> {
          KEY key = dtoKeyExtractor.apply(dto);
          ENTITY existingEntity = existingEntityMap.get(key);
          if (existingEntity != null) {
            entityUpdater.accept(existingEntity, dto);
          } else {
            toSave.add(entityCreator.apply(dto));
          }
        });

    if (!toSave.isEmpty()) {
      saveAllAction.accept(toSave); // Consumer 실행
    }
  }
}
