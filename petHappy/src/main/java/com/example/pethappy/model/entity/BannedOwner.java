package com.example.pethappy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "banned_owners")
public class BannedOwner extends BaseEntity {
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private LocalDateTime bannedTill;


    public BannedOwner() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getBannedTill() {
        return bannedTill;
    }

    public void setBannedTill(LocalDateTime bannedTill) {
        this.bannedTill = bannedTill;
    }
}
