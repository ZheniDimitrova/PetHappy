package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Category;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import com.example.pethappy.repository.CategoryRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productsRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productsRepository, CategoryRepository categoryRepository) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Product> findByCategoryName(CategoryNameEnum nameEnum) {

        Category category = categoryRepository.findByCategoryName(nameEnum);

        return productsRepository.getByCategory(category);
    }

    @Override
    public List<Product> findByPetTypeEnum(PetTypeEnum petTypeEnum) {

        return productsRepository.getByForType(petTypeEnum);
    }
}
