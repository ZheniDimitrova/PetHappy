package com.example.pethappy.service.impl;

import com.example.pethappy.model.dto.BannedOwnerImportDto;
import com.example.pethappy.model.entity.BannedOwner;
import com.example.pethappy.repository.BannedOwnerRepository;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.service.BannedOwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BannedOwnerServiceImpl implements BannedOwnerService {

    private final BannedOwnerRepository bannedOwnerRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    public BannedOwnerServiceImpl(BannedOwnerRepository bannedOwnerRepository, OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.bannedOwnerRepository = bannedOwnerRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean banCurrentOwner(BannedOwnerImportDto bannedOwnerImportDto) {

        if (!ownerRepository.existsByUsername(bannedOwnerImportDto.getUsername())){
            return false;
        }

        BannedOwner ownerToBan = modelMapper.map(bannedOwnerImportDto, BannedOwner.class);
        ownerToBan.setBannedTill(LocalDateTime.now().plusHours((long) bannedOwnerImportDto.getBannedTill()));

        bannedOwnerRepository.save(ownerToBan);

        return true;
    }

    @Override
    public boolean bannedOwnerExists(String name) {

      return bannedOwnerRepository.existsByUsername(name);

    }
}
