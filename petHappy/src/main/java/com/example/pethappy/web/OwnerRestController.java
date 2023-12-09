package com.example.pethappy.web;

import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.service.OwnerService;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OwnerRestController {

    private final OwnerService ownerService;

    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/profileInfo/{id}")
    public HttpEntity<List<String>> getProfileInfo(@PathVariable ("id") Long id) {

        Owner owner = ownerService.getOwnerById(id);
        List<String> result = new ArrayList<>();

        result.add(owner.getUsername());
        result.add(owner.getFirstName());
        result.add(owner.getLastName());
        result.add(owner.getEmail());


        return new HttpEntity<>(result);

    }


}
