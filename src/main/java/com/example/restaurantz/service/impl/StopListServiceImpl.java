package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.dto.StopList.PaginationResponseStopList;
import com.example.restaurantz.dto.StopList.StopListRequest;
import com.example.restaurantz.dto.StopList.StopListResponse;
import com.example.restaurantz.entity.MenuItem;
import com.example.restaurantz.entity.StopList;
import com.example.restaurantz.exceptions.BadRequestException;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.MenuItemRepo;
import com.example.restaurantz.repo.StopListRepo;
import com.example.restaurantz.service.StopListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    @Transactional
    public StopListResponse saveStopList(Long menuId, StopListRequest stopListRequest) {
        if (stopListRequest.getDate().isAfter(LocalDate.now()) || stopListRequest.getDate().isEqual(LocalDate.now())) {
            MenuItem menuItem = menuItemRepo.findById(menuId)
                    .orElseThrow(() -> new NotFoundException("MenuItem not found"));

            if (stopListRepo.existsByDateAndMenuItem(stopListRequest.getDate(), menuItem)) {
                throw new BadRequestException("StopList date and MenuItem already exists!");
            }

            StopList stopList = new StopList();
            stopList.setReason(stopListRequest.getReason());
            stopList.setMenuItem(menuItem);
            stopList.setDate(stopListRequest.getDate());

            menuItem.setIsBlocked(LocalDate.now());

            stopList.setMenuItem(menuItem);
            menuItem.setStopList(stopList);

            menuItemRepo.save(menuItem);
            stopListRepo.save(stopList);

            return new StopListResponse(
                    stopList.getId(),
                    stopList.getReason(),
                    menuItem.getName(),
                    stopList.getDate()
            );
        } else {
            throw new BadRequestException("Invalid date!");
        }
    }

    @Override
    public StopList findById(Long id) {
        return stopListRepo.findById(id).orElseThrow(() ->
                new NotFoundException("StopList not found"));
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepo.findById(id).orElseThrow(() ->
                new NotFoundException("StopList not found"));

        MenuItem menuItem = menuItemRepo.findMenuItemByName(stopListRequest.getMenuItemName())
                .orElseThrow(() -> new NotFoundException("MenuItem not found"));

        stopList.setReason(stopListRequest.getReason());
        stopList.setMenuItem(menuItem);
        stopList.setDate(stopListRequest.getDate());

        menuItemRepo.save(menuItem);
        stopListRepo.save(stopList);

        return SimpleResponse.builder().message("UPDATE").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        StopList stopList = stopListRepo.findById(id).orElseThrow(() ->
                new NotFoundException("StopList not found"));

        MenuItem menuItem = stopList.getMenuItem();

        menuItem.setIsBlocked(null);
        menuItem.setStopList(null);
        stopList.setMenuItem(null);
        stopListRepo.delete(stopList);
        return null;
    }
}
