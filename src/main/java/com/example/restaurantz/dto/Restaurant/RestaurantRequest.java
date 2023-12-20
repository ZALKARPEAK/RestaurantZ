package com.example.restaurantz.dto.Restaurant;

import com.example.restaurantz.enums.RestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantRequest {
    private String name;
    private String position;
    private RestType restType;
}
