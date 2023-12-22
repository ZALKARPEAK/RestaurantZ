package com.example.restaurantz.dto.AverageSum;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AverageSumResponse {
    private long sum;

    public AverageSumResponse(long sum) {
        this.sum = sum;
    }
}
