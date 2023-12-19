package com.example.pethappy.web;

import com.example.pethappy.model.dto.BannedOwnerImportDto;
import com.example.pethappy.service.BannedOwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class BannedOwnerController {

    private final BannedOwnerService bannedOwnerService;

    public BannedOwnerController(BannedOwnerService bannedOwnerService) {
        this.bannedOwnerService = bannedOwnerService;
    }

    @PostMapping("/banOwner")
    public String banOwner(BannedOwnerImportDto bannedOwnerImportDto, RedirectAttributes redirectAttributes) {

        boolean isFound = bannedOwnerService.banCurrentOwner(bannedOwnerImportDto);

        if (!isFound) {
           redirectAttributes.addFlashAttribute("isBannedUserNotFound", true);
        }

        return "redirect:/owners/admin";
    }
}
