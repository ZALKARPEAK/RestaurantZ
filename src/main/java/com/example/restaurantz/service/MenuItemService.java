package com.example.restaurantz.service;

import com.example.restaurantz.dto.MenuItem.MenuItemRequest;
import com.example.restaurantz.dto.MenuItem.MenuItemResponse;
import com.example.restaurantz.dto.MenuItem.PaginationResponseMenuItem;
import com.example.restaurantz.dto.MenuItem.SearchResponse;
import com.example.restaurantz.dto.SimpleResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuItemService {
    SimpleResponse saveMenuItem(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);
    SimpleResponse assignMenuItemToSubCategory(Long subCategoryId, Long menuItemId);
    SimpleResponse updateMenuItemById(Long menuItemId, MenuItemRequest menuItemRequest);
    SimpleResponse deleteMenuItem(Long menuItemId);
    PaginationResponseMenuItem findAllMenuItems(int pageSize, int currentPage);
    MenuItemResponse findById(Long id);
    List<SearchResponse> search(String keyword);
    List<MenuItemResponse> findAllMenuItemSortedByPriceAscAndDesc(String sort);
    List<MenuItemResponse> filter(@Param("isVegetarian") Boolean isVegetarian);
}
