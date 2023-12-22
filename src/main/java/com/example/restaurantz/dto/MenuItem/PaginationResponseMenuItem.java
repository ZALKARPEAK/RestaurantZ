package com.example.restaurantz.dto.MenuItem;

import com.example.restaurantz.dto.Category.CategoryResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record PaginationResponseMenuItem (
    List<MenuItemResponse> categoryResponseList,
    int size,
    int page
){

}
