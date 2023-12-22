package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.Category.CategoryRequest;
import com.example.restaurantz.dto.Category.CategoryResponse;
import com.example.restaurantz.dto.Category.PaginationResponseCategory;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.Category;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.CategoryRepo;
import com.example.restaurantz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public PaginationResponseCategory findAll(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<CategoryResponse> allCategory=categoryRepo.getAllCategories(pageable);
        return PaginationResponseCategory
                .builder()
                .categoryResponseList(allCategory.getContent())
                .page(allCategory.getNumber()+1)
                .size(allCategory.getTotalPages())
                .build();

    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category is saved")
                .build();
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepo.getCategoryById(id).orElseThrow(() ->
                new NotFoundException("Category not found"));
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Category not found"));

        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return SimpleResponse.builder().message("UPDATE").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Category not found"));

        categoryRepo.delete(category);
        return SimpleResponse.builder().message("Deleted").httpStatus(HttpStatus.OK).build();
    }
}
