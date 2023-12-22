package com.example.restaurantz.api;

import com.example.restaurantz.dto.MenuItem.MenuItemRequest;
import com.example.restaurantz.dto.MenuItem.MenuItemResponse;
import com.example.restaurantz.dto.MenuItem.PaginationResponseMenuItem;
import com.example.restaurantz.dto.MenuItem.SearchResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menuItem")
@PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
public class MenuItemApi {

    private final MenuItemService menuItemService;

    @PostMapping("/save/{restaurantId}/{subCategoryId}")
    public ResponseEntity<SimpleResponse> saveMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long subCategoryId,
            MenuItemRequest menuItemRequest) {
        return ResponseEntity.ok(menuItemService.saveMenuItem(restaurantId, subCategoryId, menuItemRequest));
    }

    @PostMapping("/assign/{subCategoryId}/{menuItemId}")
    public ResponseEntity<SimpleResponse> assignMenuItemToSubCategory(
            @PathVariable Long subCategoryId,
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(menuItemService.assignMenuItemToSubCategory(subCategoryId, menuItemId));
    }

    @PutMapping("/update/{menuItemId}")
    public ResponseEntity<SimpleResponse> updateMenuItemById(
            @PathVariable Long menuItemId,
            MenuItemRequest menuItemRequest) {
        return ResponseEntity.ok(menuItemService.updateMenuItemById(menuItemId, menuItemRequest));
    }

    @DeleteMapping("/delete/{menuItemId}")
    public ResponseEntity<SimpleResponse> deleteMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(menuItemService.deleteMenuItem(menuItemId));
    }

    @GetMapping("/findAll")
    public ResponseEntity<PaginationResponseMenuItem> findAllMenuItems(
            @RequestParam int pageSize,
            @RequestParam int currentPage) {
        return ResponseEntity.ok(menuItemService.findAllMenuItems(pageSize, currentPage));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MenuItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(menuItemService.search(keyword));
    }

    @GetMapping("/findAllSortedByPrice")
    public ResponseEntity<List<MenuItemResponse>> findAllMenuItemSortedByPriceAscAndDesc(
            @RequestParam String sort) {
        return ResponseEntity.ok(menuItemService.findAllMenuItemSortedByPriceAscAndDesc(sort));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MenuItemResponse>> filter(@RequestParam Boolean isVegetarian) {
        return ResponseEntity.ok(menuItemService.filter(isVegetarian));
    }
}
