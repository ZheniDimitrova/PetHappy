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
public class OwnerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerService ownerService;



    @BeforeAll
    public void setUp() {
        OwnerRegisterBindingModel ownerRegisterBindingModel = new OwnerRegisterBindingModel("Anna", "Ana", "Aneva", "anka@abv.bg", "234", "234");
        ownerService.registerOwner(ownerRegisterBindingModel);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    public void confirmRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .with(csrf())
                        .param("username", "Geri")
                        .param("firstName", "Gergana")
                        .param("lastName", "Gereva")
                        .param("email", "geri@abv.bg")
                        .param("password", "222")
                        .param("confirmPassword", "222"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
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
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }


    @Test
    @WithMockUser(username = "administrator", authorities = {"ADMINISTRATOR"})
    public void testAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/admin"))
                .andExpect(MockMvcResultMatchers.view().name("admin"));
    }

    @Test
    @WithMockUser(username = "moderator")
    public void testModerator() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/moderator"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser
    public void testAdvice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/advice"))
                .andExpect(MockMvcResultMatchers.view().name("advice"));
    }

    @Test
    @WithMockUser(username = "administrator")
    public void testProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/profile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("profileId"))
                .andExpect(MockMvcResultMatchers.view().name("profile"));

    }

    @Test
    @WithMockUser
    public void testEditCurrentProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/editProfile"))
                .andExpect(MockMvcResultMatchers.view().name("editProfile"));

    }

    @Test
    @WithMockUser("Anna")
    public void testConfirmEditProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/owners/confirmEditProfile")
                .with(csrf())
                .param("username", "Gerina")
                .param("firstName", "Gerganche")
                .param("lastName", "Gergancheva")
                .param("email", "geri12@abv.bg")
                .param("password", "222"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owners/profile"));

        mockMvc.perform(MockMvcRequestBuilders.put("/owners/confirmEditProfile")
                        .with(csrf())
                        .param("username", "Gerina")
                        .param("firstName", "Gergana")
                        .param("lastName", "G")
                        .param("email", "geri@abv.bg")
                        .param("password", "222"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owners/editProfile"));

    }

}
