package com.example.restaurantz.dto.MenuItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemRequest {
    private String name;
    private String image;
    private int price;
    private String description;
    private boolean isVegetarian;
}
