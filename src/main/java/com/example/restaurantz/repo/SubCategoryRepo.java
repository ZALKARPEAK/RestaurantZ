package com.example.restaurantz.repo;

import com.example.restaurantz.dto.SubCategory.SubCategoryResponse;
import com.example.restaurantz.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

    @Query("SELECT NEW com.example.restaurantz.dto.SubCategory.SubCategoryResponse(c.name, sc.name, sc.id) FROM SubCategory sc JOIN sc.category c GROUP BY sc.name, sc.id, c.name")
    List<SubCategoryResponse> getByGroupSupCategory();

}
