package com.example.restaurantz.entity;

import com.example.restaurantz.entity.adducation.Id;
import com.example.restaurantz.enums.RestType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Restaurant extends Id {
    private String name;
    private String position;
    @Enumerated
    private RestType restType;
    private int numberOfEmployees;
    private int service;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<JobApplication> jobApplications;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.REMOVE})
    private List<User> users;

    @OneToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH}, mappedBy = "restaurant")
    private List<MenuItem> menuItems;
}
