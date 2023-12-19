package com.example.pethappy.web;

import com.example.pethappy.service.OwnerService;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final OwnerService ownerService;

    public UserController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @ModelAttribute
    public OwnerRegisterBindingModel ownerRegisterBindingModel() {
        return  new OwnerRegisterBindingModel();
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

        return "redirect:/login";
    }
}
