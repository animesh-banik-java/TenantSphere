package com.banik.user_service.basepackage.mapper;

import java.util.List;

public interface EntityToDtoMapper<D, E> {
	E toEntity(D dto);

	D toDto(E entity);

	List<E> toEntity(List<D> dtoList);

	List<D> toDto(List<E> entityList);

}
