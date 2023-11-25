package com.example.pethappy.util;

import com.example.pethappy.model.entity.Order;
import com.example.pethappy.model.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Cart {

    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
