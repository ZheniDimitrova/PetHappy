package com.example.pethappy.model.dto;

import java.time.LocalDateTime;

public class BannedOwnerImportDto {

    private String username;

    private int bannedTill;

    public BannedOwnerImportDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBannedTill() {
        return bannedTill;
    }

    public void setBannedTill(int bannedTill) {
        this.bannedTill = bannedTill;
    }
}
