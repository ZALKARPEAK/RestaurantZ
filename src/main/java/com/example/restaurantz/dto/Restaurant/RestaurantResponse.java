package com.example.restaurantz.dto.Restaurant;

import com.example.restaurantz.enums.RestType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class RestaurantResponse {
    private String name;
    private String position;
    private RestType restType;
    private int numberOfEmployees;
    private int service;

    public RestaurantResponse(String name, String position, RestType restType, int numberOfEmployees, int service) {
        this.name = name;
        this.position = position;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
