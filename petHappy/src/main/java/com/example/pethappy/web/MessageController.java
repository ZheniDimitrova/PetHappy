package com.example.pethappy.web;

import com.example.pethappy.model.dto.MessageImportDto;
import com.example.pethappy.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/addMessage")
    public String addMessage(MessageImportDto messageImportDto) {

        messageService.addMessage(messageImportDto);


        return "redirect:/owners/moderator";
    }
}
