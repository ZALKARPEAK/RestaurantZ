package com.example.restaurantz.entity;

import com.example.restaurantz.entity.adducation.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class StopList extends Id {
    private String reason;
    private LocalDate date;

    @OneToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,CascadeType.REMOVE}, mappedBy = "stopList")
    private MenuItem menuItem;}
