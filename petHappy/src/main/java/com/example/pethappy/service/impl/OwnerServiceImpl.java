package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.model.entity.UserRole;
import com.example.pethappy.model.entity.enums.UserRoleEnum;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.repository.UserRoleRepository;
import com.example.pethappy.service.OwnerService;
import com.example.pethappy.validation.EditProfileBindingModel;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public OwnerServiceImpl(OwnerRepository ownerRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.ownerRepository = ownerRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
        admin.setTown("Sofia");
        ownerRepository.save(admin);

        Owner moderator = new Owner("moderator", passwordEncoder.encode("moderatorPasss"), "moderator@abv.bg", "Pesho", "Pashev");
        moderator.getUserRoles().add(moderatorRole);
        moderator.setTown("Burgas");
        ownerRepository.save(moderator);
    }

    @Override
    public Owner findOwnerByUsername(String username) {
        return ownerRepository.findByUsername(username);
    }

    @Override
    public void loginOwner(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public Owner getOwnerById(Long id) {

        return ownerRepository.findById(id).get();
    }

    @Override
    public void editProfileData(String username, EditProfileBindingModel editProfileBindingModel) {

        Owner owner = ownerRepository.findByUsername(username);
        owner.setUsername(editProfileBindingModel.getUsername());
        owner.setFirstName(editProfileBindingModel.getFirstName());
        owner.setLastName(editProfileBindingModel.getLastName());
        owner.setEmail(editProfileBindingModel.getEmail());

        ownerRepository.save(owner);
    }

    @Override
    public void changeCurrentRole(String username, String role) {
        Owner owner = ownerRepository.findByUsername(username);

        if (owner == null) {
            throw new IllegalArgumentException("Owner not found!");
        }

        Set<UserRole> userRoles = new HashSet<>();
        UserRole adminRole = userRoleRepository.findById(1L).get();
        UserRole moderatorRole = userRoleRepository.findById(2L).get();

        switch (role) {
            case "ADMINISTRATOR": {
                userRoles.add(adminRole);
                userRoles.add(moderatorRole);
            }
            break;

            case "MODERATOR": {
                userRoles.add(moderatorRole);
            }
                break;

            case "USER": break;
        }

        owner.setUserRoles(userRoles);
        ownerRepository.save(owner);
    }


}
