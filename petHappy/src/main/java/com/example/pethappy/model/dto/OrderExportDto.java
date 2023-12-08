package com.example.pethappy.model.dto;

import com.example.pethappy.model.entity.OrderedProduct;
import com.example.pethappy.model.entity.Owner;

import java.util.List;

public class OrderExportDto {

    private String town;

    private String address;
    private String phoneNumber;
    private String email;

    private Owner owner;

    private List<OrderedProduct> orderedProducts;

    public OrderExportDto() {
    }



    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }
}
