package com.example.pethappy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    @GetMapping()
    public String index() {
        return "index";

    }

    @GetMapping("/login")
    public String login() {
        return "login";

    }

    @PostMapping("/login")
    public String confirmLogin() {


        return "redirect:index";

    }

    @GetMapping("/register")
    public String register() {

        return "register";

    }

    @PostMapping("/register")
    public String confirmRegister() {


        return "redirect:index";

    }


}
