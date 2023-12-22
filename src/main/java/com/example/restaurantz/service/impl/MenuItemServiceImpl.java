package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.MenuItem.MenuItemRequest;
import com.example.restaurantz.dto.MenuItem.MenuItemResponse;
import com.example.restaurantz.dto.MenuItem.PaginationResponseMenuItem;
import com.example.restaurantz.dto.MenuItem.SearchResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.MenuItem;
import com.example.restaurantz.entity.Restaurant;
import com.example.restaurantz.entity.SubCategory;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.MenuItemRepo;
import com.example.restaurantz.repo.RestaurantRepo;
import com.example.restaurantz.repo.SubCategoryRepo;
import com.example.restaurantz.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;
    private final SubCategoryRepo subCategoryRepo;

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId, Long subCategoryId, MenuItemRequest request) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant not found"));
        SubCategory subCategory = subCategoryRepo.findById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setImage(request.getImage());
        menuItem.setPrice(request.getPrice());
        menuItem.setDescription(request.getDescription());
        menuItem.setVegetarian(request.isVegetarian());
        menuItem.setRestaurant(restaurant);
        menuItem.setSubcategory(subCategory);
        subCategory.setMenuItems(Collections.singletonList(menuItem));

        menuItemRepo.save(menuItem);
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public SimpleResponse assignMenuItemToSubCategory(Long subCategoryId, Long menuItemId) {
        SubCategory subCategory = subCategoryRepo.findById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory not found"));

        MenuItem menuItem = menuItemRepo.findById(menuItemId).orElseThrow(() ->
                new NotFoundException("MenuItem not found"));

        subCategory.getMenuItems().add(menuItem);
        menuItem.setSubcategory(subCategory);
        subCategoryRepo.save(subCategory);
        menuItemRepo.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Menu is successfully assigned to subcategory...")
                .build();
    }

    @Override
    public SimpleResponse updateMenuItemById(Long menuItemId, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepo.findById(menuItemId).orElseThrow(() ->
                new NotFoundException("MenuItem not found"));

        menuItem.setName(request.getName());
        menuItem.setImage(request.getImage());
        menuItem.setPrice(request.getPrice());
        menuItem.setDescription(request.getDescription());
        menuItem.setVegetarian(request.isVegetarian());

        menuItemRepo.save(menuItem);
        return SimpleResponse.builder().message("UPDATE").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteMenuItem(Long menuItemId) {
        MenuItem menuItem = menuItemRepo.findById(menuItemId).orElseThrow(() ->
                new NotFoundException("MenuItem not found"));

        menuItem.setSubcategory(null);
        menuItem.setRestaurant(null);
        menuItemRepo.delete(menuItem);
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public PaginationResponseMenuItem findAllMenuItems(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<MenuItemResponse> allMenuItem = menuItemRepo.findAllMenuItems(pageable);
        return PaginationResponseMenuItem
                .builder()
                .categoryResponseList(allMenuItem.getContent())
                .page(allMenuItem.getNumber()+1)
                .size(allMenuItem.getTotalPages())
                .build();
    }

    @Override
    public MenuItemResponse findById(Long id) {
        return menuItemRepo.getMenuItemById(id).orElseThrow(() -> new NotFoundException("MenuItem not found"));
    }

    @Override
    public List<SearchResponse> search(String keyword) {
        return menuItemRepo.search(keyword);
    }

    @Override
    public List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort) {
        if(sort.equalsIgnoreCase("ASC")){
            return menuItemRepo.getAllByOrderByPriceAsc();
        }
        if(sort.equalsIgnoreCase("DESC")){
            return menuItemRepo.getAllByOrderByPriceDesc();
        }

        return new ArrayList<>();
    }

    @Override
    public List<MenuItemResponse> filter(Boolean isVegetarian) {
        return menuItemRepo.filter(isVegetarian);
    }
}
