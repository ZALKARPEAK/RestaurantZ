package com.example.restaurantz.api;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.SubCategory.SubCategoryRequest;
import com.example.restaurantz.dto.SubCategory.SubCategoryResponse;
import com.example.restaurantz.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subCategory")
@PreAuthorize("hasAuthority('ADMIN')")
public class SubCategoryApi {

    private final SubCategoryService subCategoryService;

    @PostMapping("/{categoryId}/save")
    public ResponseEntity<SimpleResponse> saveSubCategory(
            @PathVariable Long categoryId,
            SubCategoryRequest subcategoryRequest) {
        SimpleResponse response = subCategoryService.saveSubCategory(categoryId, subcategoryRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/grouped")
    public ResponseEntity<List<SubCategoryResponse>> getByGroupSupCategory() {
        List<SubCategoryResponse> subCategories = subCategoryService.getByGroupSupCategory();
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @PutMapping("/{subCategoryId}/update")
    public ResponseEntity<SimpleResponse> updateSubCategoryById(
            @PathVariable Long subCategoryId,
            SubCategoryRequest request) {
        SimpleResponse response = subCategoryService.updateSubCategoryById(subCategoryId, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{categoryId}/{subCategoryId}/delete")
    public ResponseEntity<SimpleResponse> deleteSubCategory(
            @PathVariable Long categoryId,
            @PathVariable Long subCategoryId) {
        SimpleResponse response = subCategoryService.deleteSubCategory(categoryId, subCategoryId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
