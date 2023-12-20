package com.example.restaurantz.repo;

import com.example.restaurantz.dto.Restaurant.RestaurantResponse;
import com.example.restaurantz.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    Restaurant findRestaurantByName(String restaurantName);
    @Query("SELECT NEW com.example.restaurantz.dto.Restaurant.RestaurantResponse(r.name, r.position, r.restType, r.numberOfEmployees, r.service) FROM Restaurant r")
    List<RestaurantResponse> getAllRestaurant();
    RestaurantResponse findRestaurantById(Long restaurantId);
}
