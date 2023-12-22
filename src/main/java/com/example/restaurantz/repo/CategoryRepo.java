package com.example.restaurantz.repo;

import com.example.restaurantz.dto.Category.CategoryResponse;
import com.example.restaurantz.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query("select new com.example.restaurantz.dto.Category.CategoryResponse(c.id,c.name) from Category c")
    Page<CategoryResponse> getAllCategories(Pageable pageable);

    Optional<CategoryResponse> getCategoryById(Long id);
}
