package com.example.restaurantz.api;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.StopList.PaginationResponseStopList;
import com.example.restaurantz.dto.StopList.StopListRequest;
import com.example.restaurantz.dto.StopList.StopListResponse;
import com.example.restaurantz.entity.StopList;
import com.example.restaurantz.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stopList")
@PreAuthorize("hasAnyAuthority('ADMIN', 'WALTER')")
public class StopListApi {

    private final StopListService stopListService;

    @GetMapping("/all")
    public PaginationResponseStopList getAllStopLists(@RequestParam int pageSize, @RequestParam int currentPage) {
        return stopListService.findAll(pageSize, currentPage);
    }

    @PostMapping("/save/{menuId}")
    public StopListResponse saveStopList(@PathVariable Long menuId, StopListRequest stopListRequest) {
        return stopListService.saveStopList(menuId, stopListRequest);
    }

    @GetMapping("/get/{id}")
    public StopList getStopListById(@PathVariable Long id) {
        return stopListService.findById(id);
    }

    @PutMapping("/update/{id}")
    public SimpleResponse updateStopList(@PathVariable Long id, StopListRequest stopListRequest) {
        return stopListService.update(id, stopListRequest);
    }

    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteStopListById(@PathVariable Long id) {
        return stopListService.deleteById(id);
    }
}
