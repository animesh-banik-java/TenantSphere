package com.banik.user_service.mapper;

import com.banik.user_service.basepackage.mapper.RequestToEntityMapper;
import com.banik.user_service.entity.User;
import com.banik.user_service.request.UserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRequestDTOMapper extends RequestToEntityMapper<UserRequestDTO, User> {
}
