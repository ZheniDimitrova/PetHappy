package com.example.pethappy.web;

import com.example.pethappy.service.OrderService;
import com.example.pethappy.validation.OrderBindingModel;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/makeOrder")
    public String confirmOrder(@Valid OrderBindingModel orderBindingModel, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderBindingModel", orderBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderBindingModel", bindingResult);

            return "redirect:/cart";
        }


        orderService.addNewOrder(orderBindingModel, userDetails.getUsername());

        return "shop";

    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable ("orderId") Long id) {

        orderService.deleteCurrentOrder(id);

        return "redirect:/owners/moderator";
    }

}
