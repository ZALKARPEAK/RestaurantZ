package com.example.restaurantz.api;

import com.example.restaurantz.dto.Category.CategoryRequest;
import com.example.restaurantz.dto.Category.PaginationResponseCategory;
import com.example.restaurantz.dto.Category.CategoryResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<PaginationResponseCategory> getAllCategories(
            @RequestParam int pageSize,
            @RequestParam int currentPage) {
        PaginationResponseCategory response = categoryService.findAll(pageSize, currentPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<SimpleResponse> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        SimpleResponse response = categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SimpleResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        SimpleResponse response = categoryService.update(id, categoryRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SimpleResponse> deleteCategoryById(@PathVariable Long id) {
        SimpleResponse response = categoryService.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
