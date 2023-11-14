package com.example.pethappy.service;

import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import com.example.pethappy.model.entity.enums.PetTypeEnum;

import java.util.List;

public interface ProductService {

    List<Product> findByCategoryName(CategoryNameEnum nameEnum);

    List<Product> findByPetTypeEnum(PetTypeEnum petTypeEnum);
}
