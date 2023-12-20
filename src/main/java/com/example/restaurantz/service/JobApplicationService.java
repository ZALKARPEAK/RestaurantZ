package com.example.restaurantz.service;

import com.example.restaurantz.dto.JobApplication.JobApplicationRequest;
import com.example.restaurantz.dto.SimpleResponse;

public interface JobApplicationService {
    SimpleResponse submitApplication(JobApplicationRequest request, Long restaurantId);
}
