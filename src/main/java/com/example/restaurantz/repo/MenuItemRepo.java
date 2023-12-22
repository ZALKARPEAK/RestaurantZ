package com.example.restaurantz.repo;

import com.example.restaurantz.dto.MenuItem.MenuItemResponse;
import com.example.restaurantz.dto.MenuItem.SearchResponse;
import com.example.restaurantz.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
    @Query("select new com.example.restaurantz.dto.MenuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m")
    Page<MenuItemResponse> findAllMenuItems(Pageable pageable);

    Optional<MenuItemResponse> getMenuItemById(Long id);

    @Query("SELECT NEW com.example.restaurantz.dto.MenuItem.SearchResponse(m.subcategory.category.name,m.subcategory.name , m.name, m.image, m.price) FROM MenuItem m WHERE m.name ILIKE '%' || ?1 || '%'")
    List<SearchResponse> search(String keyWord);

    List<MenuItemResponse> getAllByOrderByPriceAsc();

    List<MenuItemResponse> getAllByOrderByPriceDesc();

    @Query("select new com.example.restaurantz.dto.MenuItem.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m WHERE m.isVegetarian = :isVag")
    List<MenuItemResponse> filter(@Param("isVag") boolean isVag);

    Optional<MenuItem> findMenuItemByName(String name);
}
