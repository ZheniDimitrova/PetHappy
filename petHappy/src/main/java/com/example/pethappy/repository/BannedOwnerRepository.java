package com.example.pethappy.repository;

import com.example.pethappy.model.entity.BannedOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedOwnerRepository extends JpaRepository<BannedOwner, Long> {

    boolean existsByUsername(String name);

}
