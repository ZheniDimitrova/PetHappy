package com.example.pethappy.validation;

import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddProductBindingModel {
    @NotBlank(message = "Моля, изберете категория")
    private String forType;

    @Size(min = 2, max = 20, message = "Моля, въведете името на продукта")
    private String productName;

    @Size(min = 2, max = 2000, message = "Моля, добавете описание на продукта")
    private String description;

    @Positive(message = "Невалидна цена")
    private BigDecimal price;

    private MultipartFile picture;

    public AddProductBindingModel() {

    }

    public String getForType() {
        return forType;
    }

    public void setForType(String forType) {
        this.forType = forType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
