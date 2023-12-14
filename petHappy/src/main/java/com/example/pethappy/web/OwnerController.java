package com.example.pethappy.web;

import com.example.pethappy.model.dto.OrderExportDto;
import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.service.OrderService;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.validation.AddProductBindingModel;
import com.example.pethappy.validation.EditProfileBindingModel;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final OrderService orderService;


    public OwnerController(OwnerService ownerService, OrderService orderService) {
        this.ownerService = ownerService;
        this.orderService = orderService;
    }

    @GetMapping()
    public String index() {
        return "index";

    }


    @ModelAttribute
    public OwnerRegisterBindingModel ownerRegisterBindingModel() {
        return  new OwnerRegisterBindingModel();
    }

    @ModelAttribute
    public EditProfileBindingModel editProfileBindingModel() {
        return new EditProfileBindingModel();
    }




    @GetMapping("/register")
    public String register() {

        return "register";

    }

    @PostMapping("/register")
    public String confirmRegister(@Valid OwnerRegisterBindingModel ownerRegisterBindingModel, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if (bindingResult.hasErrors() || !ownerRegisterBindingModel.getPassword().equals(ownerRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("ownerRegisterBindingModel", ownerRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ownerRegisterBindingModel", bindingResult);

            return "redirect:register";
        }

        ownerService.registerOwner(ownerRegisterBindingModel);
        ownerService.loginOwner(ownerRegisterBindingModel.getUsername());
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return "redirect:/";

    }



    @GetMapping("/login")
    public String login() {
        return "login";

    }

    @PostMapping("/login")
    public String confirmLogin() {

        return "redirect:index";

    }

    @PostMapping("/login-error")
    public String errorCredentials(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("invalidCredentials", true);

        return "redirect:/owners/login";
    }

    @GetMapping("/profile")
    public String myProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        Owner owner = ownerService.findOwnerByUsername(userDetails.getUsername());
        model.addAttribute("profileId", owner.getId());

        return "profile";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        if (!model.containsAttribute("addProductBindingModel")){
            model.addAttribute("addProductBindingModel",new AddProductBindingModel());
        }

        return "admin";
    }

    @PatchMapping("/changeRole")
    public String changeRole(@RequestParam("username") String username, @RequestParam("role") String role, @AuthenticationPrincipal UserDetails userDetails, HttpSession httpSession,
                             RedirectAttributes redirectAttributes) {

        try {
            ownerService.changeCurrentRole(username, role);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("isUserNotFound", true);
        }


        if (userDetails.getUsername().equals(username)){
            SecurityContextHolder.clearContext();
            httpSession.invalidate();
        }

        return "redirect:/owners/admin";
    }

    @GetMapping("/moderator")
    public String moderator(Model model) {

       List<OrderExportDto> orders = orderService.getAllOrders();
       model.addAttribute("ordersList", orders);

        return "moderator";
    }



    @GetMapping("/advice")
    public String advice() {

        return "advice";
    }



    @GetMapping("/editProfile")
    public String editCurrentProfile() {

        return "editProfile";
    }

    @PutMapping("/confirmEditProfile")
    public String confirmEditProfile(@AuthenticationPrincipal UserDetails userDetails,@Valid EditProfileBindingModel editProfileBindingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editProfileBindingModel", editProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editProfileBindingModel", bindingResult);

           return "redirect:/editProfile";
        }

        ownerService.editProfileData(userDetails.getUsername(), editProfileBindingModel);
        SecurityContextHolder.clearContext();
        httpSession.invalidate();

        return "redirect:/owners/profile";
    }



}
