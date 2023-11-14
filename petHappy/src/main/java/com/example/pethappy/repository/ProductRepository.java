package com.example.pethappy.repository;

import com.example.pethappy.model.entity.Category;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getByCategory(Category category);

    List<Product> getByForType(PetTypeEnum forType);
}
