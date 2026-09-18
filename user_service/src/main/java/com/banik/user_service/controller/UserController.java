package com.banik.user_service.controller;

import com.banik.user_service.basepackage.controller.BaseController;
import com.banik.user_service.basepackage.request.PaginationRequest;
import com.banik.user_service.basepackage.response.Response;
import com.banik.user_service.request.FilterRequest;
import com.banik.user_service.request.UserRequestDTO;
import com.banik.user_service.response.UserResponseDTO;
import com.banik.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Response> addUser(@RequestBody @Valid final UserRequestDTO request) {
        UserResponseDTO user = userService.addUser(request);
        return data(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable final Long userId, @RequestBody @Valid final UserRequestDTO request) {
        UserResponseDTO user = userService.updateUser(userId, request);
        return data(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable final Long userId) {
        return data(userService.getUserById(userId));
    }

    @PutMapping("/{userId}/change-status")
    public ResponseEntity<Response> changeStatus(@PathVariable final Long userId) {
        return data(userService.changeStatus(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable final Long userId) {
        return data(userService.deleteUser(userId));
    }

    @GetMapping
    public ResponseEntity<Response> getUsers(FilterRequest filterRequest, PaginationRequest request) {
        return data(userService.getUsers(filterRequest, request));
    }
}
