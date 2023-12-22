package com.example.restaurantz.dto.AverageSum;

import lombok.Data;

import java.util.List;

@Data
public class ChequeRequest {
    private List<Long> menuItemNames;
}
