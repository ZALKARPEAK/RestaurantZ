package com.example.restaurantz.dto.Category;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginationResponseCategory(
        List<CategoryResponse> categoryResponseList,
        int size,
        int page
) {
}