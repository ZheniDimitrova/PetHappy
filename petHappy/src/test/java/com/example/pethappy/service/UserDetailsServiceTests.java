package com.example.pethappy.service;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {
    @Mock
    private OwnerRepository ownerRepository;

    private UserDetailsServiceImpl toTest;

    @BeforeEach
    public void setUp() {
        toTest = new UserDetailsServiceImpl(ownerRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        //Arrange
        Owner owner = new Owner();
        owner.setUsername("Gosho");
        owner.setPassword("somePass");
        owner.setUserRoles(new HashSet<>());
        Mockito.when(ownerRepository.findByUsername("Gosho")).thenReturn(owner);

        //Act
        UserDetails userDetails = toTest.loadUserByUsername("Gosho");
        //Assert
        Assertions.assertEquals(owner.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(owner.getPassword(), userDetails.getPassword());

    }
}
