package com.example.restaurantz.dto.Authentication;

import com.example.restaurantz.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @Email
    private String email;
    private String password;
    @NotNull
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
    private int experience;

}