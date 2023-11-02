package com.example.pethappy.validation;

import jakarta.validation.constraints.Size;

public class OwnerLoginBindingModel {
    @Size(min = 4, max = 15, message = "Username length must be between 4 and 15 characters!")
    private String username;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;


    public OwnerLoginBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
