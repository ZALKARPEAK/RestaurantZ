package com.example.restaurantz.dto.SubCategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCategoryRequest {
    private String name;

    public SubCategoryRequest(String name) {
        this.name = name;
    }
}
