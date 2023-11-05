package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public OwnerServiceImpl(OwnerRepository ownerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerOwner(OwnerRegisterBindingModel ownerRegisterBindingModel) {

        Owner owner = modelMapper.map(ownerRegisterBindingModel, Owner.class);

        if (ownerRepository.findByUsername(owner.getUsername()) != null) {
            return;
        }

         String encodePassword = passwordEncoder.encode(owner.getPassword());
        owner.setPassword(encodePassword);

        ownerRepository.save(owner);

    }


}
