package com.example.restaurantz.entity;

import com.example.restaurantz.entity.adducation.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Category extends Id {
    private String name;

    @OneToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "category")
    private List<SubCategory> subCategories;
}
