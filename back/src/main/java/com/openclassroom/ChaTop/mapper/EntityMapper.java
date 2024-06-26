package com.openclassroom.ChaTop.mapper;

import java.util.List;

public interface EntityMapper<Dto, Entity> {
  Entity toEntity(Dto dto);

  Dto toDto(Entity entity);

  List<Entity> toEntity(List<Dto> dtoList);

  List<Dto> toDto(List<Entity> entityList);
}
