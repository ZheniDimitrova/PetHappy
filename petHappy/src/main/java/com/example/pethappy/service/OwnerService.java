package com.example.pethappy.service;

import com.example.pethappy.validation.OwnerRegisterBindingModel;

public interface OwnerService {
    void registerOwner(OwnerRegisterBindingModel ownerRegisterBindingModel);

    void initRegisterOwner();
}
