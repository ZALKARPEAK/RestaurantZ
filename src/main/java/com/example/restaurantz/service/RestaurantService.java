package com.example.restaurantz.service;

import com.example.restaurantz.dto.Restaurant.RestaurantRequest;
import com.example.restaurantz.dto.Restaurant.RestaurantResponse;
import com.example.restaurantz.dto.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse createRestaurant(RestaurantRequest restaurant);
    List<RestaurantResponse> getAllRestaurants();
    RestaurantResponse getRestaurantById(Long restaurantId);
    SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest updatedRestaurant);
    SimpleResponse deleteRestaurant(Long restaurantId);
}
