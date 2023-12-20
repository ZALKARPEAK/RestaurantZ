package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.JobApplication.JobApplicationRequest;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.JobApplication;
import com.example.restaurantz.entity.Restaurant;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.JobApplicationRepo;
import com.example.restaurantz.repo.RestaurantRepo;
import com.example.restaurantz.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final RestaurantRepo restaurantRepo;
    private final JobApplicationRepo jobApplicationRepo;

    @Override
    public SimpleResponse submitApplication(JobApplicationRequest request, Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant not found"));

        if(restaurant != null){
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
        }

        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }
}
