package com.example.pethappy.model.entity;

import com.example.pethappy.model.entity.enums.UserRoleEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "userRoles")
public class UserRole extends BaseEntity{
    @Column
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;
    @ManyToMany(mappedBy = "userRoles")
    private Set<Owner> owners;

    public UserRole() {
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }
}
