package com.example.restaurantz.service;

import com.example.restaurantz.dto.AverageSum.AverageSumResponse;
import com.example.restaurantz.dto.AverageSum.ChequeRequest;
import com.example.restaurantz.dto.AverageSum.ChequeTotalWaiterResponse;
import com.example.restaurantz.dto.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    SimpleResponse saveCheque(Long userId, List<Long> menuItem);
    SimpleResponse updateCheque(Long id, List<Long> menuItem);
    ChequeTotalWaiterResponse chequeTotalByWaiter(Long waiterId, LocalDate date);
    SimpleResponse deleteCheque(Long id);
    AverageSumResponse getAverageSum(LocalDate date);
    AverageSumResponse getAverageSumOfWaiter(Long waiterId, LocalDate dateTime);
}
