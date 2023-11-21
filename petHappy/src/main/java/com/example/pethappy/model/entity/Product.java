package com.example.pethappy.model.entity;

import com.example.pethappy.model.entity.enums.PetTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int count;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetTypeEnum forType;
    @Column(nullable = false)
    private BigDecimal price;
    @OneToOne
    private Picture picture;



    @ManyToOne
    private Category category;
    @ManyToOne
    private Owner owner;


    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PetTypeEnum getForType() {
        return forType;
    }

    public void setForType(PetTypeEnum forType) {
        this.forType = forType;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
