package com.banik.user_service.service;


import com.banik.user_service.basepackage.request.PaginationRequest;
import com.banik.user_service.request.FilterRequest;
import com.banik.user_service.request.UserRequestDTO;
import com.banik.user_service.response.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponseDTO addUser(final UserRequestDTO request);

    UserResponseDTO updateUser(final Long userId, final UserRequestDTO request);

    UserResponseDTO getUserById(final Long userId);

    UserResponseDTO deleteUser(final Long userId);

    UserResponseDTO changeStatus(final Long userId);

    Page<UserResponseDTO> getUsers(final FilterRequest filterRequest, final PaginationRequest pageRequest);
}
