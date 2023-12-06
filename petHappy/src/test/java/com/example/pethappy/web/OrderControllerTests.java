package com.example.pethappy.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Rocko")
    public void testConfirmOrder() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/makeOrder").with(csrf())
                .param("firstName", "Gosho")
                .param("lastName", "Georgiev")
                .param("town", "Sofia")
                .param("address", "Vitosha 15")
                .param("phoneNumber", "0123456789")
                .param("email", "gosho@abv.bg"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("shop"));

        mockMvc.perform(MockMvcRequestBuilders.post("/makeOrder").with(csrf())
                        .param("firstName", "G")
                        .param("lastName", "Georgiev")
                        .param("town", "Sofia")
                        .param("address", "Vitosha 15")
                        .param("phoneNumber", "012345")
                        .param("email", "gosho@abv.bg"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cart"));

    }
}
