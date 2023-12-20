package com.example.restaurantz.api;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.User.UserRequest;
import com.example.restaurantz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;

    @PostMapping("/AdminSaveEmployee")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> adminSaveEmployee(@RequestBody UserRequest request){
        userService.adminRegisterEmployee(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/acceptApplication/{jobUser}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> acceptApplication(@PathVariable Long jobUser){
        userService.acceptApplication(jobUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
