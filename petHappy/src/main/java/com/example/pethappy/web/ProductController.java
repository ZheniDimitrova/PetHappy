package com.example.pethappy.web;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import com.example.pethappy.service.PictureService;
import com.example.pethappy.service.ProductService;
import com.example.pethappy.util.Cart;
import com.example.pethappy.validation.AddProductBindingModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final PictureService pictureService;
    private final Cart cart;

    public ProductController(ProductService productService, PictureService pictureService, Cart cart) {
        this.productService = productService;
        this.pictureService = pictureService;
        this.cart = cart;
    }

    @GetMapping("/products/{type}")
    public String products(Model model, @PathVariable("type") String type) {

        PetTypeEnum petTypeEnum = PetTypeEnum.valueOf(type);
        List<ProductExportDto> productsList = productService.findByPetTypeEnum(petTypeEnum);

        model.addAttribute("products", productsList);
        model.addAttribute("productsCount", cart.getProducts().size());

        return "products";
    }

    @GetMapping("/currentProduct/{id}")
    public String currentProduct(Model model, @PathVariable("id") Long id) {

        ProductExportDto currentProduct = productService.getProductDtoById(id);
        model.addAttribute("productId", currentProduct.getId());
        model.addAttribute("productTitle", currentProduct.getName());
        model.addAttribute("productDesc", currentProduct.getDescription());
        model.addAttribute("productPrice", currentProduct.getPrice());
        model.addAttribute("productsCount", cart.getProducts().size());


        return "currentProduct";

    }

    @PostMapping("/products/addProduct")
    public String confirmAddProduct(@Valid AddProductBindingModel addProductBindingModel, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            //redirectAttributes.addFlashAttribute("addProductBindingModel", addProductBindingModel);
            //redirectAttributes.addAttribute("org.springframework.validation.BindingResult.addProductBindingModel", bindingResult);
            bindingResult.getAllErrors().stream().forEach(e -> System.out.println(e.getDefaultMessage()));

        } else {
            try {
                productService.addProduct(addProductBindingModel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "redirect:/owners/admin";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id, @RequestParam int count) {

        ProductExportDto product = productService.getProductDtoById(id);
        product.setCount(count);

        cart.getProducts().add(product);



        return "redirect:/currentProduct/" + id;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) throws ParseException {

        List<ProductExportDto> productList = cart.getProducts();
        double finalPrice = productService.sumFinalPrice(productList);

        model.addAttribute("productsList", productList);
        model.addAttribute("finalPrice", finalPrice);

        return "cart";
    }

}
