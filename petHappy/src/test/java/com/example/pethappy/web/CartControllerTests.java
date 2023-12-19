package com.example.pethappy.web;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.util.Cart;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Cart cart;

    @Autowired
    private OwnerService ownerService;




    @BeforeAll
    public void setUp() {
        cart.getProducts().add(new ProductExportDto());
        cart.getProducts().add(new ProductExportDto());
        cart.getProducts().add(new ProductExportDto());

        OwnerRegisterBindingModel ownerRegisterBindingModel = new OwnerRegisterBindingModel("Roko", "Rocko", "Rockov", "roko@abv.bg", "123", "123");
        ownerService.registerOwner(ownerRegisterBindingModel);
    }



    @Test
    @WithMockUser(username = "Roko")
    public void testViewCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("productsList", "finalPrice", "orderBindingModel", "ownerFirstName", "ownerLastName", "ownerEmail"))
                .andExpect(MockMvcResultMatchers.view().name("cart"));
    }


}
