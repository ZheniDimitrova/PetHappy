package com.example.pethappy.model.entity;

import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryNameEnum categoryName;
    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
