package com.example.pethappy.model.entity;

import com.example.pethappy.model.entity.enums.PetTypeEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetTypeEnum type;
    @Column(nullable = false)
    private String breed;
    @Column(nullable = false)
    private String name;
    @Column
    private Integer age;
    @Column
    private LocalDate birthDate;
    @ManyToOne
    private Owner owner;



    public Pet() {
    }

    public PetTypeEnum getType() {
        return type;
    }

    public void setType(PetTypeEnum type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
