package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.JobApplication.JobApplicationRequest;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.JobApplication;
import com.example.restaurantz.entity.Restaurant;
import com.example.restaurantz.enums.Role;
import com.example.restaurantz.exceptions.BadRequestException;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.JobApplicationRepo;
import com.example.restaurantz.repo.RestaurantRepo;
import com.example.restaurantz.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final RestaurantRepo restaurantRepo;
    private final JobApplicationRepo jobApplicationRepo;
    private static final String PHONE_NUMBER_REGEX = "^\\+?[0-9-]+";


    @Override
    public SimpleResponse submitApplication(JobApplicationRequest request, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));

        int age = Period.between(request.getDateOfBirth(), LocalDate.now()).getYears();
        if ((request.getRole() == Role.CHEF && (age < 25 || age > 45))
                || (request.getRole() == Role.WALTER && (age < 18 || age > 30))) {
            throw new BadRequestException("Invalid age for the specified role.");
        }

        if ((request.getRole() == Role.CHEF && request.getExperience() < 2)
                || (request.getRole() == Role.WALTER && request.getExperience() < 1)) {
            throw new BadRequestException("Insufficient experience for the specified role.");
        }

        if (jobApplicationRepo.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email must be unique.");
        }

        if (!isValidPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Invalid phone number format.");
        }

        if (request.getPassword().length() < 4) {
            throw new BadRequestException("Password must be at least 4 characters long.");
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setFirstName(request.getFirstName());
        jobApplication.setLastName(request.getLastName());
        jobApplication.setDateOfBirth(request.getDateOfBirth());
        jobApplication.setEmail(request.getEmail());
        jobApplication.setPassword(request.getPassword());
        jobApplication.setPhoneNumber(request.getPhoneNumber());
        jobApplication.setRole(request.getRole());
        jobApplication.setExperience(request.getExperience());
        jobApplication.setRestaurant(restaurant);

        jobApplicationRepo.save(jobApplication);

        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber);
    }
}
