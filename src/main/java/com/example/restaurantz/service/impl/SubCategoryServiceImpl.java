package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.SubCategory.SubCategoryRequest;
import com.example.restaurantz.dto.SubCategory.SubCategoryResponse;
import com.example.restaurantz.entity.Category;
import com.example.restaurantz.entity.SubCategory;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.CategoryRepo;
import com.example.restaurantz.repo.SubCategoryRepo;
import com.example.restaurantz.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepo subCategoryRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subcategoryRequest) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category not found"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subcategoryRequest.getName());
        subCategory.setCategory(category);

        subCategoryRepo.save(subCategory);
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public List<SubCategoryResponse> getByGroupSupCategory() {
        return subCategoryRepo.getByGroupSupCategory();
    }

    @Override
    public SimpleResponse updateSubCategoryById(Long subCategoryId, SubCategoryRequest request) {
        SubCategory subCategory = subCategoryRepo.findById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory not found"));

        subCategory.setName(request.getName());
        subCategoryRepo.save(subCategory);
        return SimpleResponse.builder().message("UPDATE").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteSubCategory(Long categoryId, Long subCategoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category not found"));
        SubCategory subCategory = subCategoryRepo.findById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory not found"));

        if(category.getSubCategories().contains(subCategory)){
            category.getSubCategories().remove(subCategory);
            subCategory.setCategory(null);
            subCategory.setMenuItems(null);
            subCategoryRepo.delete(subCategory);
        }else {
            throw new NotFoundException("SubCategory not found");
        }

        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }
}
