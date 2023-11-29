package com.example.pethappy.web;

import com.example.pethappy.service.OwnerService;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping()
    public String index() {
        return "index";

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
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !ownerRegisterBindingModel.getPassword().equals(ownerRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("ownerRegisterBindingModel", ownerRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ownerRegisterBindingModel", bindingResult);

            return "redirect:register";
        }

        ownerService.registerOwner(ownerRegisterBindingModel);

        return "redirect:index";

    }



    @GetMapping("/login")
    public String login() {
        return "login";

    }

    @PostMapping("/login")
    public String confirmLogin() {


        return "redirect:index";

    }

    @GetMapping("/admin")
    public String admin() {

        return "admin";
    }

    @GetMapping("/moderator")
    public String moderator() {

        return "moderator";
    }

    @PostMapping("/login-error")
    public String errorCredentials(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("invalidCredentials", true);

        return "redirect:/owners/login";
    }

}
