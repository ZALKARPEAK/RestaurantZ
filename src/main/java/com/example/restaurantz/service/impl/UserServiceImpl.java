package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.User.UserRequest;
import com.example.restaurantz.entity.JobApplication;
import com.example.restaurantz.entity.Restaurant;
import com.example.restaurantz.entity.User;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.JobApplicationRepo;
import com.example.restaurantz.repo.RestaurantRepo;
import com.example.restaurantz.repo.UserRepo;
import com.example.restaurantz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public SimpleResponse acceptApplication(Long jobUser) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restaurant restaurant = authenticatedUser.getRestaurant();
        JobApplication request = restaurant.getJobApplications().stream()
                .filter(jobApplication -> jobApplication.getId() == jobUser).findFirst()
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

        restaurant.setUsers(Collections.singletonList(user));

        request.setRestaurant(null);
        jobApplicationRepo.delete(request);

        restaurantRepo.save(restaurant);
        userRepo.save(user);

        return null;
    }

    @Override
    public SimpleResponse rejectApplication(Long userId) {
        return null;
    }
}
