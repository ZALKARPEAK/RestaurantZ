package com.example.restaurantz.dto.Authentication;

import com.example.restaurantz.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String email;
    private Role role;
}
