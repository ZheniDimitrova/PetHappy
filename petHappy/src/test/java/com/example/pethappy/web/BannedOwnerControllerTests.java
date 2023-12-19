package com.example.pethappy.web;

import com.example.pethappy.service.OwnerService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BannedOwnerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerService ownerService;



    @BeforeAll
    public void setUp() {
        OwnerRegisterBindingModel ownerRegisterBindingModel = new OwnerRegisterBindingModel("Nikolaus", "Nikolai", "Nikolaev", "nikos@abv.bg", "234", "234");
        ownerService.registerOwner(ownerRegisterBindingModel);
    }

    @Test
    @WithMockUser
    public void testBanOwner() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/banOwner")
                .with(csrf())
                        .param("username", "Nikolaus" )
                        .param("bannedTill", "24"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owners/admin"));

    }
}
