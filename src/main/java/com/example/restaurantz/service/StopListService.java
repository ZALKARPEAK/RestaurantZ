package com.example.restaurantz.service;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.StopList.PaginationResponseStopList;
import com.example.restaurantz.dto.StopList.StopListRequest;
import com.example.restaurantz.dto.StopList.StopListResponse;
import com.example.restaurantz.entity.StopList;

public interface StopListService {
    PaginationResponseStopList findAll(int pageSize, int currentPage);
    StopListResponse saveStopList(Long menuId, StopListRequest request);
    StopList findById(Long id);
    SimpleResponse update(Long id, StopListRequest stopListRequest);
    SimpleResponse deleteById(Long id);
}
