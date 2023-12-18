package com.example.pethappy.service;

import com.example.pethappy.model.dto.MessageExportDto;
import com.example.pethappy.model.dto.MessageImportDto;


import java.util.List;

public interface MessageService {

    List<MessageExportDto> getMessages();

    void initMessages();

    void addMessage(MessageImportDto messageImportDto);
}
