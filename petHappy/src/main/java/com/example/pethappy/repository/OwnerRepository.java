package com.example.pethappy.repository;

import com.example.pethappy.model.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {


    Owner findByUsername(String username);

    boolean existsByUsername(String username);
}
