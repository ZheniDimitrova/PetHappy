package com.example.pethappy.service;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.model.entity.UserRole;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.repository.UserRoleRepository;
import com.example.pethappy.service.impl.OwnerServiceImpl;
import com.example.pethappy.validation.OwnerRegisterBindingModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class OwnerServiceTests {
    @Mock
    private  OwnerRepository ownerRepository;
    @Mock
    private  UserRoleRepository userRoleRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  UserDetailsService userDetailsService;

    private OwnerServiceImpl toTest;

    @BeforeEach
    public void setUp() {
        toTest = new OwnerServiceImpl(ownerRepository, userRoleRepository, modelMapper, passwordEncoder, userDetailsService);
    }

    @Test
    public void testRegisterOwnerSuccessfully() {

        //Arrange
        Owner owner = Mockito.mock(Owner.class);
        OwnerRegisterBindingModel ownerRegisterBindingModel = Mockito.mock(OwnerRegisterBindingModel.class);

        Mockito.when(ownerRepository.findByUsername("")).thenReturn(null);
        Mockito.when(ownerRepository.findByUsername("Gosho")).thenReturn(owner);
        Mockito.when(modelMapper.map(ownerRegisterBindingModel, Owner.class)).thenReturn(owner);
        Mockito.when(owner.getPassword()).thenReturn("somePass");

        //Act
        toTest.registerOwner(ownerRegisterBindingModel);

        //Assert
        Mockito.verify(ownerRepository).save(owner);
        Mockito.verify(passwordEncoder).encode("somePass");

    }


    @Test
    public void testFindOwnerByUsername() {

        //Arrange
        Owner owner = new Owner();
        owner.setUsername("Gosho");
        Mockito.when(ownerRepository.findByUsername("Gosho")).thenReturn(owner);

        //Act
        Owner owner1 = toTest.findOwnerByUsername("Gosho");

        //Assert
        Assertions.assertEquals("Gosho", owner1.getUsername());
    }

    @Test
    public void testInitRegisterOwner() {

        //Arrange
        Mockito.when(ownerRepository.count()).thenReturn((long) 0);

        //Act
        toTest.initRegisterOwner();

        //Assert
        Mockito.verify(userRoleRepository, Mockito.times(2)).save(any());
        Mockito.verify(ownerRepository, Mockito.times(2)).save(any());
    }

    @Test
    public void testInitRegisterOwnerInNotNullDb() {

        //Arrange
        Mockito.when(ownerRepository.count()).thenReturn((long) 15);

        //Act
        toTest.initRegisterOwner();

        //Assert
        Mockito.verify(userRoleRepository, Mockito.times(0)).save(any());
        Mockito.verify(ownerRepository, Mockito.times(0)).save(any());
    }
}
