package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.User.UserRequest;
import com.example.restaurantz.dto.User.UserResponse;
import com.example.restaurantz.entity.JobApplication;
import com.example.restaurantz.entity.Restaurant;
import com.example.restaurantz.entity.User;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.JobApplicationRepo;
import com.example.restaurantz.repo.RestaurantRepo;
import com.example.restaurantz.repo.UserRepo;
import com.example.restaurantz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final JobApplicationRepo jobApplicationRepo;

    @Override
    public SimpleResponse adminRegisterEmployee(UserRequest request) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authenticatedUser != null && !userRepo.existsByEmail(request.getEmail())){
            User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setDateOfBirth(request.getDateOfBirth());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setPhoneNumber(request.getPhoneNumber());
                    user.setRole(request.getRole());
                    user.setExperience(request.getExperience());
            Restaurant restaurant = authenticatedUser.getRestaurant();
            user.setRestaurant(restaurant);

            List<User> users = new ArrayList<>();
            users.add(user);

            restaurant.setUsers(users);

            restaurantRepo.save(restaurant);
            userRepo.save(user);
        }

        return SimpleResponse.builder().message("SAVED").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public SimpleResponse acceptApplication(Long userId) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = authenticatedUser.getRestaurant();
        JobApplication request = restaurant.getJobApplications().stream()
                .filter(jobApplication -> jobApplication.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("JobApplication not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setExperience(request.getExperience());
        user.setRestaurant(restaurant);

        List<User> users = new ArrayList<>();
        users.add(user);
        restaurant.setUsers(users);

        restaurant.getJobApplications().remove(request);
        request.setRestaurant(null);
        restaurantRepo.save(restaurant);
        userRepo.save(user);
        jobApplicationRepo.delete(request);
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse rejectApplication(Long userId) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = authenticatedUser.getRestaurant();
        JobApplication request = restaurant.getJobApplications().stream()
                .filter(jobApplication -> jobApplication.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("JobApplication not found"));

        restaurant.getJobApplications().remove(request);
        request.setRestaurant(null);
        restaurantRepo.save(restaurant);
        jobApplicationRepo.delete(request);
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse updateUserById(Long id, UserRequest userRequest) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Restaurant restaurant = authenticatedUser.getRestaurant();


        User user = restaurant.getUsers().stream().filter(user1 -> user1.getId() == id).findFirst()
                        .orElseThrow(() -> new NotFoundException("User not found"));

        Hibernate.initialize(restaurant.getUsers());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());
        user.setExperience(userRequest.getExperience());

        userRepo.save(user);
        return SimpleResponse.builder().message("UPDATE").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Restaurant restaurant = authenticatedUser.getRestaurant();

        User user = restaurant.getUsers().stream().filter(user1 -> user1.getId() == id).findFirst()
                .orElseThrow(() -> new NotFoundException("User not found"));


        user.setRestaurant(null);
        userRepo.delete(user);
        return SimpleResponse.builder().message("Deleted").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new NotFoundException("User not found"));

        return UserResponse.builder().firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .experience(user.getExperience()).build();
    }
}
