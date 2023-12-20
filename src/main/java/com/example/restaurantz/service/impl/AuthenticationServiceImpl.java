package com.example.restaurantz.service.impl;

import com.example.restaurantz.config.JwtService;
import com.example.restaurantz.dto.Authentication.AuthenticationResponse;
import com.example.restaurantz.dto.Authentication.SignInRequest;
import com.example.restaurantz.dto.Authentication.SignUpRequest;
import com.example.restaurantz.entity.User;
import com.example.restaurantz.enums.Role;
import com.example.restaurantz.exceptions.AlreadyExistException;
import com.example.restaurantz.repo.UserRepo;
import com.example.restaurantz.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if(userRepo.existsByEmail(request.getEmail())){
            throw new AlreadyExistException(String.format("User with email: %s already exists", request.getEmail()));
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setExperience(request.getExperience());
        userRepo.save(user);

        String jwt = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setRole(user.getRole());
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        User user = userRepo.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException(
                        "User with email: " + request.getEmail() + " not found"
                ));

        if (request.getEmail().isBlank()) {
            throw new BadCredentialsException("Email doesn't exist");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        String jwt = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setRole(user.getRole());
        authenticationResponse.setToken(jwt);

        return authenticationResponse;
    }

    @PostConstruct
    public void initAdmin() {
        User user = new User();
                user.setFirstName("Admin");
                user.setLastName("Super");
                user.setDateOfBirth(LocalDate.of(2003,3,13));
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRole(Role.ADMIN);
                user.setPhoneNumber("+996999999999");
                user.setExperience(5);

        if (!userRepo.existsByEmail(user.getEmail())) {
            userRepo.save(user);
        }
    }
}
