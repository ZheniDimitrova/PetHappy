package com.example.pethappy.init;

import com.example.pethappy.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    private final ProductService productService;

    public DbInit(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        productService.initProducts();

    }
}
