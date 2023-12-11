package com.example.pethappy.web;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.service.PictureService;
import com.example.pethappy.service.ProductService;
import com.example.pethappy.validation.AddProductBindingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ProductService productService;

    @Autowired
    private PictureService pictureService;

    @BeforeAll
    public void setUp() throws IOException {
        AddProductBindingModel addProductBindingModel = new AddProductBindingModel("CAT", "food", "description", BigDecimal.valueOf(13.20), 8);
        Picture picture = pictureService.uploadPicture(new MockMultipartFile("name", new byte[255]));
        productService.addProduct(addProductBindingModel, picture);
    }




    @Test
    public void testConfirmAddProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products/addProduct")
                .with(csrf())
                .param("forType", "CAT")
                .param("productName", "food")
                .param("description", "the best food")
                .param("price", "12.50")
                .param("storageCount", "12")
                .param("picture", "null"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owners/admin"));

    }

    @Test
    @WithMockUser
    public void testAddToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addToCart/15")//productId must exist
                .with(csrf())
                .param("count", "2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/currentProduct/15"));

    }

    @Test
    public void testProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/DOG"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productsCount"))
                .andExpect(MockMvcResultMatchers.view().name("products"));
    }

    @Test
    @WithMockUser
    public void testCurrentProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currentProduct/14"))// productId must exist
                .andExpect(MockMvcResultMatchers.model().attributeExists("productId"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productTitle"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productDesc"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productPrice"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productsCount"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("maxProductCount"))
                .andExpect(MockMvcResultMatchers.view().name("currentProduct"));
    }



}
