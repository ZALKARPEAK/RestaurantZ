package com.example.restaurantz.api;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.User.UserRequest;
import com.example.restaurantz.dto.User.UserResponse;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<SimpleResponse> acceptApplication(@PathVariable Long jobUser){
        userService.acceptApplication(jobUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/rejectApplication/{jobUser}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<SimpleResponse> rejectApplication(@PathVariable Long jobUser) {
        userService.rejectApplication(jobUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/updateUser/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        userService.updateUserById(userId, userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<SimpleResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
