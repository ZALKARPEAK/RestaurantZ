package com.example.restaurantz.api;

import com.example.restaurantz.dto.JobApplication.JobApplicationRequest;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/JobApplication")
public class JobApplicationApi {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/JobRequest/{restaurantId}")
    public ResponseEntity<SimpleResponse> request(@RequestBody JobApplicationRequest request,@PathVariable Long restaurantId){
        jobApplicationService.submitApplication(request, restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
