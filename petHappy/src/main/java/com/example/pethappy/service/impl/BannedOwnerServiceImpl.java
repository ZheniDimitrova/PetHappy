package com.example.pethappy.service.impl;

import com.example.pethappy.model.dto.BannedOwnerImportDto;
import com.example.pethappy.model.entity.BannedOwner;
import com.example.pethappy.repository.BannedOwnerRepository;
import com.example.pethappy.service.BannedOwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BannedOwnerServiceImpl implements BannedOwnerService {

    private final BannedOwnerRepository bannedOwnerRepository;
    private final ModelMapper modelMapper;

    public BannedOwnerServiceImpl(BannedOwnerRepository bannedOwnerRepository, ModelMapper modelMapper) {
        this.bannedOwnerRepository = bannedOwnerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void banCurrentOwner(BannedOwnerImportDto bannedOwnerImportDto) {

        BannedOwner ownerToBan = modelMapper.map(bannedOwnerImportDto, BannedOwner.class);
        ownerToBan.setBannedTill(LocalDateTime.now().plusHours((long) bannedOwnerImportDto.getBannedTill()));

        bannedOwnerRepository.save(ownerToBan);
    }

    @Override
    public boolean bannedOwnerExists(String name) {

      return bannedOwnerRepository.existsByUsername(name);

    }
}
