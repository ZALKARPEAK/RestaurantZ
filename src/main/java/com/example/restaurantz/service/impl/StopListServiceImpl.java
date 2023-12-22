package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.MenuItem.MenuItemResponse;
import com.example.restaurantz.dto.MenuItem.PaginationResponseMenuItem;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.StopList.PaginationResponseStopList;
import com.example.restaurantz.dto.StopList.StopListRequest;
import com.example.restaurantz.dto.StopList.StopListResponse;
import com.example.restaurantz.entity.StopList;
import com.example.restaurantz.repo.MenuItemRepo;
import com.example.restaurantz.repo.StopListRepo;
import com.example.restaurantz.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {

    private final MenuItemRepo menuItemRepo;
    private final StopListRepo stopListRepo;

    @Override
    public PaginationResponseStopList findAll(int pageSize, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<StopListResponse> allStopList = stopListRepo.findAllStopLists(pageable);
        return PaginationResponseStopList
                .builder()
                .all(allStopList.getContent())
                .page(allStopList.getNumber() + 1)
                .size(allStopList.getTotalPages())
                .build();
    }

    @Override
    public StopListResponse saveStopList(Long menuId, StopListRequest request) {




        return null;
    }

    @Override
    public StopList findById(Long id) {
        return null;
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        return null;
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        return null;
    }
}
