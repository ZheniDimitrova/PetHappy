package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.model.entity.UserRole;
import com.example.pethappy.model.entity.enums.UserRoleEnum;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.repository.UserRoleRepository;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public OwnerServiceImpl(OwnerRepository ownerRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.userRoleRepository = userRoleRepository;
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

    @Override
    public void initRegisterOwner() {

        if(ownerRepository.count() != 0) return;

        UserRole adminRole = new UserRole(UserRoleEnum.ADMINISTRATOR);
        UserRole moderatorRole = new UserRole(UserRoleEnum.MODERATOR);

        userRoleRepository.save(adminRole);
        userRoleRepository.save(moderatorRole);

        Owner admin = new Owner("administrator", passwordEncoder.encode("adminPasss"),"admin@abv.bg", "Pasha", "Pesheva" );
        admin.getUserRoles().add(adminRole);
        admin.getUserRoles().add(moderatorRole);
        ownerRepository.save(admin);

        Owner moderator = new Owner("moderator", passwordEncoder.encode("moderatorPasss"), "moderator@abv.bg", "Pesho", "Pashev");
        moderator.getUserRoles().add(moderatorRole);
        ownerRepository.save(moderator);
    }

    @Override
    public Owner findOwnerByUsername(String username) {
        return ownerRepository.findByUsername(username);
    }


}
