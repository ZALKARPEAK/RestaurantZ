package com.example.restaurantz.dto.StopList;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StopListRequest {
    @NotEmpty(message = "fill in the field")
    private String reason;
    @NotEmpty(message = "fill in the field")
    private String menuItemName;
    private LocalDate date;

    public StopListRequest(String reason, String menuItemName, LocalDate date) {
        this.reason = reason;
        this.menuItemName = menuItemName;
        this.date = date;
    }
}