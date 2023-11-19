package com.example.pethappy.web;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import com.example.pethappy.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{type}")
    public String products(Model model, @PathVariable("type") String type) {

        PetTypeEnum petTypeEnum = PetTypeEnum.valueOf(type);
        List<ProductExportDto> productsList = productService.findByPetTypeEnum(petTypeEnum);

        model.addAttribute("products", productsList);

        return "products";
    }

    @GetMapping("/currentProduct/{id}")
    public String currentProduct(Model model, @PathVariable("id") Long id) {


        return "currentProduct";

    }

}
