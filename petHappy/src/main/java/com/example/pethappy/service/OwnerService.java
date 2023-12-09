package com.example.pethappy.service;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.validation.OwnerRegisterBindingModel;

public interface OwnerService {
    void registerOwner(OwnerRegisterBindingModel ownerRegisterBindingModel);

    void initRegisterOwner();

    Owner findOwnerByUsername(String username);

    void loginOwner(String username);

    Owner getOwnerById(Long id);
}
