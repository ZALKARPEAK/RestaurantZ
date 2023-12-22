package com.example.restaurantz.dto.Category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {
    @NotEmpty(message = "fill in the field")
    private String name;
}
