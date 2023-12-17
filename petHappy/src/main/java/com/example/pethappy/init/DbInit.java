package com.example.pethappy.init;

import com.example.pethappy.service.MessageService;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

        private final OwnerService ownerService;

        private final MessageService messageService;

    public DbInit(ProductService productService, OwnerService ownerService, MessageService messageService) {
        this.ownerService = ownerService;
        this.messageService = messageService;
    }

    @Override
    public void run(String... args) throws Exception {

        ownerService.initRegisterOwner();

        messageService.initMessages();
    }
}
