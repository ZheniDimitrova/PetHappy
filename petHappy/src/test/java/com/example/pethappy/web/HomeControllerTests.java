package com.example.pethappy.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAboutUs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/aboutUs"))
                .andExpect(MockMvcResultMatchers.view().name("aboutUs"));
    }

    @Test
    public void testShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/shop"))
                .andExpect(MockMvcResultMatchers.view().name("shop"));
    }

    @Test
    public void testContacts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpect(MockMvcResultMatchers.view().name("contacts"));
    }
}
