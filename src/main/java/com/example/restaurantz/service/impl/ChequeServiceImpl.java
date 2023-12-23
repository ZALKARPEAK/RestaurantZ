package com.example.restaurantz.service.impl;

import com.example.restaurantz.dto.AverageSum.AverageSumResponse;
import com.example.restaurantz.dto.AverageSum.ChequeTotalWaiterResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.entity.Cheque;
import com.example.restaurantz.entity.MenuItem;
import com.example.restaurantz.entity.User;
import com.example.restaurantz.enums.Role;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.repo.ChequeRepo;
import com.example.restaurantz.repo.MenuItemRepo;
import com.example.restaurantz.repo.UserRepo;
import com.example.restaurantz.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepo chequeRepo;
    private final UserRepo userRepo;
    private final MenuItemRepo menuItemRepo;

    @Override
    public SimpleResponse saveCheque(Long userId, List<Long> menuItem) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found"));

        List<MenuItem> menuItems = new ArrayList<>();

        if(user.getRole().name().equals("WALTER")) {
            for (Long id : menuItem) {
                MenuItem menuItem1 = menuItemRepo.findById(id).orElseThrow(() ->
                        new NotFoundException("MenuItem not found"));
                menuItems.add(menuItem1);

                Cheque cheque = new Cheque();
                cheque.setCreatedAt(LocalDate.now());

                int allPrice = 0;
                for (MenuItem m : menuItems) {
                    allPrice += m.getPrice();
                }

                menuItem1.setChequeList(Collections.singletonList(cheque));
                cheque.setPriceAverage(allPrice);
                cheque.setUser(user);
                cheque.setMenuItems(menuItems);
                chequeRepo.save(cheque);
            }
        }else {
            throw new RuntimeException("Role not Walter");
        }
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse updateCheque(Long id, List<Long> menuItemIds) {
        Cheque existingCheque = chequeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cheque not found"));

        List<MenuItem> menuItems = menuItemRepo.findAllById(menuItemIds);

        // Clear existing associations to avoid orphaned entities
        existingCheque.getMenuItems().forEach(menuItem -> menuItem.getChequeList().remove(existingCheque));

        // Update the menu items in the existing cheque
        existingCheque.setMenuItems(menuItems);

        // Update the cheque reference in the menu items
        for (MenuItem m : menuItems) {
            m.getChequeList().add(existingCheque);
            menuItemRepo.save(m);
        }

        // Recalculate the average price based on the updated menu items
        int allPrice = menuItems.stream().mapToInt(MenuItem::getPrice).sum();
        existingCheque.setPriceAverage(allPrice);

        // Save the updated cheque
        chequeRepo.save(existingCheque);

        return SimpleResponse.builder().message("Cheque updated successfully").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ChequeTotalWaiterResponse chequeTotalByWaiter(Long waiterId, LocalDate date) {
        int counterCheck = 0;
        int totalPrice = 0;

        User waiter = userRepo.findById(waiterId).orElseThrow(() ->
                new NotFoundException("Waiter not found"));

        if (waiter.getRole().equals(Role.WALTER)) {
            for (Cheque cheque : waiter.getCheque()) {
                if (cheque.getCreatedAt().isEqual(date)) {
                    int service = cheque.getPriceAverage() * waiter.getRestaurant().getService() / 100;
                    totalPrice += service + cheque.getPriceAverage();
                    counterCheck++;
                }
            }
        }

        return ChequeTotalWaiterResponse.builder()
                .waiterName(waiter.getFirstName() + " " + waiter.getLastName())
                .counterCheck(counterCheck)
                .date(date)
                .totalPrice(totalPrice)
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {
        Cheque cheque = chequeRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Cheque not found"));

        List<MenuItem> menuItem = cheque.getMenuItems();
        for (MenuItem m:menuItem) {
            m.setChequeList(null);
            menuItemRepo.save(m);

        }
        cheque.setUser(null);
        chequeRepo.delete(cheque);
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public AverageSumResponse getAverageSum(LocalDate date) {
        return chequeRepo.getChequeByCreatedAtAndAndPriceAverage(date);
    }

    @Override
    public AverageSumResponse getAverageSumOfWaiter(Long waiterId, LocalDate dateTime) {
        User user = userRepo.findById(waiterId).orElseThrow(() ->
                new NotFoundException("User not found"));

        AverageSumResponse averageSumResponse = null;

        if (user.getRole().name().equalsIgnoreCase("WALTER")) {
            averageSumResponse = chequeRepo.getChequeByCreatedAtAndPriceAverageAndUserId(dateTime, waiterId);
        }

        return averageSumResponse;
    }

}
