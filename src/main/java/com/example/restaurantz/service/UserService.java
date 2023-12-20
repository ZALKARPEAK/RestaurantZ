package com.example.restaurantz.service;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.User.UserRequest;

public interface UserService {
    SimpleResponse adminRegisterEmployee(UserRequest request);
    SimpleResponse acceptApplication(Long userId);
    SimpleResponse rejectApplication(Long userId);
}
