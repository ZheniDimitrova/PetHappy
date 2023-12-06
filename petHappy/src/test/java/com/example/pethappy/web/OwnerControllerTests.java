package com.example.pethappy.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OwnerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/register"))
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    public void confirmRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/register")
                .with(csrf())
                .param("username", "Geri")
                .param("firstName", "Gergana")
                .param("lastName", "Gereva")
                .param("email", "geri@abv.bg")
                .param("password", "222")
                .param("confirmPassword", "222"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        mockMvc.perform(MockMvcRequestBuilders.post("/owners/register")
                        .with(csrf())
                        .param("username", "Geri")
                        .param("firstName", "Gergana")
                        .param("lastName", "G")
                        .param("email", "geri@abv.bg")
                        .param("password", "222")
                        .param("confirmPassword", "2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("register"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/login"))
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

//    @Test
//    public void confirmLogin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/owners/login")
//                .with(csrf())
//                .param("username", "Gosho")
//                .param("password", "111"))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
//
//
//    }
}
