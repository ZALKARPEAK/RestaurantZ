package com.example.restaurantz.entity;

import com.example.restaurantz.entity.adducation.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class StopList extends Id {
    private String reason;
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "stopList")
    private MenuItem menuItem;

    public StopList(String reason, String menuItemName, LocalDate date) {
        super();
    }

    public StopList() {
    }
}
