package com.banik.user_service.basepackage.mapper;

import java.util.List;

public interface RequestToEntityMapper<R, E> {

	E toEntity(R request);

	List<E> toEntity(List<R> requestList);

}
