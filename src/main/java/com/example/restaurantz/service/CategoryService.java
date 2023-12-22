package com.example.restaurantz.service;

import com.example.restaurantz.dto.Category.CategoryRequest;
import com.example.restaurantz.dto.Category.CategoryResponse;
import com.example.restaurantz.dto.Category.PaginationResponseCategory;
import com.example.restaurantz.dto.SimpleResponse;

public interface CategoryService {
    PaginationResponseCategory findAll(int pageSize, int currentPage);
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse findById(Long id);
    SimpleResponse update(Long id, CategoryRequest categoryRequest);
    SimpleResponse deleteById(Long id);
}
