package com.example.pethappy.web;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.service.ProductService;
import com.example.pethappy.util.Cart;
import com.example.pethappy.validation.OrderBindingModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final OwnerService ownerService;

    public CartController(Cart cart, ProductService productService, OwnerService ownerService) {
        this.cart = cart;
        this.productService = productService;
        this.ownerService = ownerService;
    }

    @GetMapping("/cart")
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) throws ParseException {

        List<ProductExportDto> productList = cart.getProducts();
        double finalPrice = productService.sumFinalPrice(productList);

        model.addAttribute("productsList", productList);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("orderBindingModel", new OrderBindingModel());

        Owner owner = ownerService.findOwnerByUsername(userDetails.getUsername());

        model.addAttribute("ownerFirstName", owner.getFirstName());
        model.addAttribute("ownerLastName", owner.getLastName());
        model.addAttribute("ownerEmail", owner.getEmail());



        return "cart";
    }

    @GetMapping("/removeFromCart/{index}")
    public String removeFromCart(@PathVariable("index") int index) {

        ProductExportDto exportDto = cart.getProducts().remove(index);

        Product product = productService.findProductById(exportDto.getId());

        productService.updateProductStorageCount(product, exportDto.getCount());
        return "redirect:/cart";
    }




}
