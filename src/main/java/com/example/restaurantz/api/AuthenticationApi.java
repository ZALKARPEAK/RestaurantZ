package com.example.restaurantz.api;

import com.example.restaurantz.dto.Authentication.AuthenticationResponse;
import com.example.restaurantz.dto.Authentication.SignInRequest;
import com.example.restaurantz.dto.Authentication.SignUpRequest;
import com.example.restaurantz.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}