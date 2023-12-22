package com.example.restaurantz.dto.StopList;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginationResponseStopList (
    List<StopListResponse> all,
    int page,
    int size
){}
