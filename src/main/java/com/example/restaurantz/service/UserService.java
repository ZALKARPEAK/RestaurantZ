package com.example.restaurantz.service;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.User.UserRequest;
import com.example.restaurantz.dto.User.UserResponse;

public interface UserService {
    SimpleResponse adminRegisterEmployee(UserRequest request);
    SimpleResponse acceptApplication(Long userId);
    SimpleResponse rejectApplication(Long userId);
    SimpleResponse updateUserById(Long id, UserRequest userRequest);
    SimpleResponse deleteUserById(Long id);
    UserResponse getUserById(Long id);
}
