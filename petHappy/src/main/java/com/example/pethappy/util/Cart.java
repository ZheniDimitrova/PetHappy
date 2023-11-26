package com.example.pethappy.util;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Order;
import com.example.pethappy.model.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Cart {

    private List<ProductExportDto> products;

    public Cart() {
        this.products = new ArrayList<>();
    }


    public List<ProductExportDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductExportDto> products) {
        this.products = products;
    }
}
