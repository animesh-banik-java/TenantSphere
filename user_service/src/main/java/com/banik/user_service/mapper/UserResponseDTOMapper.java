package com.banik.user_service.mapper;

import com.banik.user_service.basepackage.mapper.EntityToDtoMapper;
import com.banik.user_service.entity.User;
import com.banik.user_service.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserResponseDTOMapper extends EntityToDtoMapper<UserResponseDTO, User> {
}
