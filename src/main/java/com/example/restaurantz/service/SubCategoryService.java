package com.example.restaurantz.service;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.SubCategory.SubCategoryRequest;
import com.example.restaurantz.dto.SubCategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subcategoryRequest);
    List<SubCategoryResponse> getByGroupSupCategory();
    SimpleResponse updateSubCategoryById(Long subCategoryId, SubCategoryRequest subCategoryRequest);
    SimpleResponse deleteSubCategory(Long categoryId,Long subCategoryId);
}
