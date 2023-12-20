package com.example.restaurantz.dto.Authentication;

import com.example.restaurantz.validator.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInRequest {
    @NotNull(message = "fill in the field")
    @Email
    private String email;
    @NotNull(message = "fill in the field")
    @Password(message = "Not null")
    private String password;
}
