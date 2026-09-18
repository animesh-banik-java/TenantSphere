package com.banik.user_service.repository;

import com.banik.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByEmail(final String email);

    Optional<User> findByName(final String name);

    Optional<User> findUserByPhoneNo(final String phoneNo);
}
