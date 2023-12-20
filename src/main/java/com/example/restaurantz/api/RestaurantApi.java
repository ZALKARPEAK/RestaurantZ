package com.example.restaurantz.api;

import com.example.restaurantz.dto.Restaurant.RestaurantRequest;
import com.example.restaurantz.dto.Restaurant.RestaurantResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> createRestaurant(@RequestBody RestaurantRequest restaurant) {
        SimpleResponse response = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WALTER', 'CHEF')")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, restaurants.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long restaurantId) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/update/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody RestaurantRequest updatedRestaurant) {
        SimpleResponse response = restaurantService.updateRestaurant(restaurantId, updatedRestaurant);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/delete/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> deleteRestaurant(@PathVariable Long restaurantId) {
        SimpleResponse response = restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
