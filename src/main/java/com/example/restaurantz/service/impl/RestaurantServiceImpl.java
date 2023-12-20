package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.Restaurant.RestaurantRequest;
import com.example.restaurantz.dto.Restaurant.RestaurantResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.Restaurant;
import com.example.restaurantz.exceptions.AlreadyExistException;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.RestaurantRepo;
import com.example.restaurantz.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;

    @Override
    public SimpleResponse createRestaurant(RestaurantRequest restaurant) {
        Restaurant existingRestaurant = restaurantRepo.findRestaurantByName(restaurant.getName());

        if (existingRestaurant != null) {
            throw new AlreadyExistException("Restaurant already exists");
        }

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurant.getName());
        newRestaurant.setPosition(restaurant.getPosition());
        newRestaurant.setRestType(restaurant.getRestType());
        newRestaurant.setNumberOfEmployees(0);
        newRestaurant.setService(0);

        restaurantRepo.save(newRestaurant);

        return SimpleResponse.builder().message("Restaurant created successfully").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepo.getAllRestaurant();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant not found"));

        return RestaurantResponse.builder().name(restaurant.getName())
                .position(restaurant.getPosition())
                .restType(restaurant.getRestType()).build();
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest updatedRestaurant) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant not found"));

        if(restaurant != null){
            restaurant.setName(updatedRestaurant.getName());
            restaurant.setPosition(updatedRestaurant.getPosition());
            restaurant.setRestType(updatedRestaurant.getRestType());
            restaurantRepo.save(restaurant);
        }

        return SimpleResponse.builder().message("Restaurant update successfully").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant not found"));

        if(restaurant != null){
            restaurantRepo.delete(restaurant);
        }
        return SimpleResponse.builder().message("Restaurant deleted successfully").httpStatus(HttpStatus.OK).build();
    }
}
