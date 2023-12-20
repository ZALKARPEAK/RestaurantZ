package com.example.restaurantz.entity;

import com.example.restaurantz.entity.adducation.Id;
import com.example.restaurantz.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class JobApplication extends Id {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int experience;

    @ManyToOne
    private Restaurant restaurant;
}
