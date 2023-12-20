package com.example.restaurantz.service;

import com.example.restaurantz.dto.Authentication.AuthenticationResponse;
import com.example.restaurantz.dto.Authentication.SignInRequest;
import com.example.restaurantz.dto.Authentication.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest request);
    AuthenticationResponse signIn(SignInRequest request);
}
