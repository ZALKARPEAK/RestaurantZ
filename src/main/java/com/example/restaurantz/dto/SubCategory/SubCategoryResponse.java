package com.example.restaurantz.dto.SubCategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponse {
    private String categoryName;
    private String name;
    private Long id;

    public SubCategoryResponse(String categoryName, String name, Long id) {
        this.categoryName = categoryName;
        this.name = name;
        this.id = id;
    }
}
