package com.banik.user_service.service;


import com.banik.user_service.basepackage.enums.Status;
import com.banik.user_service.basepackage.exception.ApplicationException;
import com.banik.user_service.basepackage.request.PaginationRequest;
import com.banik.user_service.basepackage.response.ResponseCode;
import com.banik.user_service.entity.User;
import com.banik.user_service.mapper.UserRequestDTOMapper;
import com.banik.user_service.mapper.UserResponseDTOMapper;
import com.banik.user_service.repository.UserRepository;
import com.banik.user_service.request.FilterRequest;
import com.banik.user_service.request.UserRequestDTO;
import com.banik.user_service.response.UserResponseDTO;
import com.banik.user_service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserRequestDTOMapper requestMapper;

    private final UserResponseDTOMapper responseMapper;


    @Override
    public UserResponseDTO addUser(final UserRequestDTO request) {
        repository.findByName(request.getName().trim())
                .ifPresent(user -> {
                    throw new ApplicationException(ResponseCode.DUPLICATE, "User already exist with same name");
                });
        repository.findUserByPhoneNo(request.getName().trim())
                .ifPresent(user -> {
                    throw new ApplicationException(ResponseCode.DUPLICATE, "Phone number already exist");
                });

        User user = requestMapper.toEntity(request);
        repository.save(user);
        return responseMapper.toDto(user);
    }

    @Override
    public UserResponseDTO updateUser(final Long userId, final UserRequestDTO request) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResponseCode.NO_CONTENT, "User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName().trim());
        }

        if (Strings.isNotBlank(request.getEmail())) {
            user.setEmail(request.getEmail().trim());
        }

        if (Strings.isNotBlank(request.getPhoneNo())) {
            user.setPhoneNo(request.getPhoneNo().trim());
        }

        if (Strings.isNotBlank(request.getAddress())) {
            user.setAddress(request.getAddress().trim());
        }

        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        repository.save(user);
        return responseMapper.toDto(user);

    }

    @Override
    public UserResponseDTO getUserById(final Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResponseCode.NO_CONTENT, "User not found"));

        return responseMapper.toDto(user);
    }

    @Override
    public UserResponseDTO deleteUser(final Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResponseCode.NO_CONTENT, "User not found"));

        repository.delete(user);
        return responseMapper.toDto(user);
    }

    @Override
    public UserResponseDTO changeStatus(final Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ResponseCode.NO_CONTENT, "User not found"));

        user.setStatus(user.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);

        repository.save(user);
        return responseMapper.toDto(user);
    }

    @Override
    public Page<UserResponseDTO> getUsers(final FilterRequest filterRequest, final PaginationRequest pageRequest) {

        Pageable pageable = PageRequest
                .of(pageRequest.getPageNo(), pageRequest.getPageSize(), Sort.by(pageRequest.getSortDirection(), pageRequest.getSortBy()));

        Specification<User> specification = UserSpecification.whereAll();

        if (Strings.isNotBlank(filterRequest.getName())) {
            specification = specification.and(UserSpecification.whereName(filterRequest.getName().trim()));
        }

        if (Strings.isNotBlank(filterRequest.getPhoneNo())) {
            specification = specification.and(UserSpecification.wherePhone(filterRequest.getPhoneNo().trim()));
        }

        if (Strings.isNotBlank(filterRequest.getEmail())) {
            specification = specification.and(UserSpecification.whereEmail(filterRequest.getEmail().trim()));
        }

        Page<User> users = repository.findAll(specification, pageable);

        return users.map(responseMapper::toDto);
    }
}
