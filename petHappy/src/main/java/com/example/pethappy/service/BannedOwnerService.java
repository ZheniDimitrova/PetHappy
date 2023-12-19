package com.example.pethappy.service;

import com.example.pethappy.model.dto.BannedOwnerImportDto;

public interface BannedOwnerService {

    boolean banCurrentOwner(BannedOwnerImportDto bannedOwnerImportDto);

    boolean bannedOwnerExists(String name);
}
