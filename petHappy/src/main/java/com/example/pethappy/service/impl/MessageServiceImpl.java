package com.example.pethappy.service.impl;

import com.example.pethappy.model.dto.MessageExportDto;
import com.example.pethappy.model.dto.MessageImportDto;
import com.example.pethappy.model.entity.Message;
import com.example.pethappy.repository.MessageRepository;
import com.example.pethappy.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<MessageExportDto> getMessages() {

        return messageRepository.findAll().stream()
                .filter(message -> message.getStartOn().isBefore(LocalDate.now()) && message.getEndOn().isAfter(LocalDate.now()))
                .map(message -> modelMapper.map(message, MessageExportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void initMessages() {

        if (messageRepository.count() != 0) {
            return;
        }

        Message message = new Message("Коледно работно време: Пон - Пет: 10:00 - 17:00", LocalDate.now().minusDays(7), LocalDate.now().plusDays(7));
        Message message2 = new Message("Почивни дни: 22-27.12 и 31.12", LocalDate.now().minusDays(3), LocalDate.now().plusDays(3));

        messageRepository.save(message);
        messageRepository.save(message2);
    }

    @Override
    public void addMessage(MessageImportDto messageImportDto) {

        Message newMessage = modelMapper.map(messageImportDto, Message.class);
        messageRepository.save(newMessage);
    }


}
