package com.example.pethappy.init;

import com.example.pethappy.service.OwnerService;
import com.example.pethappy.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

        private final OwnerService ownerService;

    public DbInit(ProductService productService, OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void run(String... args) throws Exception {

        ownerService.initRegisterOwner();



    }
}
