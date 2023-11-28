package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.model.entity.UserRole;
import com.example.pethappy.repository.OwnerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public UserDetailsServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Owner owner = ownerRepository.findByUsername(username);

        return getDetails(owner);
    }

    private UserDetails getDetails(Owner owner) {
        return new User(owner.getUsername(), owner.getPassword(), getAuthorities(owner.getUserRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<UserRole> userRoles) {
        return userRoles.stream().map(this::getAuthority).collect(Collectors.toList());
    }

    private GrantedAuthority getAuthority(UserRole userRole) {
        return new SimpleGrantedAuthority(userRole.getUserRole().name());
    }

}
