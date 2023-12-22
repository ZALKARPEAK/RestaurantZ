package com.example.restaurantz.dto.AverageSum;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChequeTotalWaiterResponse {
    private String waiterName;
    private LocalDate date;
    private int counterCheck;
    private int totalPrice;
}
