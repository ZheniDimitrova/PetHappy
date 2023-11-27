package com.example.pethappy.web;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.service.ProductService;
import com.example.pethappy.util.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.util.List;

@Controller
public class CartController {

    private final Cart cart;
    private final ProductService productService;

    public CartController(Cart cart, ProductService productService) {
        this.cart = cart;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) throws ParseException {

        List<ProductExportDto> productList = cart.getProducts();
        double finalPrice = productService.sumFinalPrice(productList);

        model.addAttribute("productsList", productList);
        model.addAttribute("finalPrice", finalPrice);

        return "cart";
    }

    @GetMapping("/removeFromCart/{index}")
    public String removeFromCart(@PathVariable("index") int index) {

        cart.getProducts().remove(index);
        return "redirect:/cart";
    }
}
