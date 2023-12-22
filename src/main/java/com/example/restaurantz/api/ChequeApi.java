package com.example.restaurantz.api;

import com.example.restaurantz.dto.AverageSum.AverageSumResponse;
import com.example.restaurantz.dto.AverageSum.ChequeTotalWaiterResponse;
import com.example.restaurantz.dto.SimpleResponse;
import com.example.restaurantz.exceptions.NotFoundException;
import com.example.restaurantz.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheque")
public class ChequeApi {

    private final ChequeService chequeService;

    @PostMapping("/save/{userId}")
    public ResponseEntity<SimpleResponse> saveCheque(@PathVariable Long userId, @RequestBody List<Long> menuItemIds) {
        try {
            SimpleResponse response = chequeService.saveCheque(userId, menuItemIds);
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(SimpleResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(SimpleResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{chequeId}")
    public ResponseEntity<SimpleResponse> updateCheque(@PathVariable Long chequeId, @RequestBody List<Long> menuItemIds) {
        try {
            SimpleResponse response = chequeService.updateCheque(chequeId, menuItemIds);
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (NotFoundException e) {
            return new ResponseEntity<>(SimpleResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(SimpleResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/totalByWaiter/{waiterId}")
    public ResponseEntity<ChequeTotalWaiterResponse> getTotalByWaiter(
            @PathVariable Long waiterId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ChequeTotalWaiterResponse response = chequeService.chequeTotalByWaiter(waiterId, date);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{chequeId}")
    public SimpleResponse delete(@PathVariable("chequeId") Long id ){
        chequeService.deleteCheque(id);
        return SimpleResponse.builder().build();
    }

    @GetMapping("/averageSum")
    public ResponseEntity<AverageSumResponse> getAverageSum(LocalDate date) {
        AverageSumResponse response = chequeService.getAverageSum(date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/averageSumOfWaiter")
    public ResponseEntity<AverageSumResponse> getAverageSumOfWaiter(@RequestParam Long waiterId,
                                                                    LocalDate dateTime) {
        AverageSumResponse response = chequeService.getAverageSumOfWaiter(waiterId, dateTime);
        return ResponseEntity.ok(response);
    }
}
