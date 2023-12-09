package com.example.pethappy.service.impl;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Category;
import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import com.example.pethappy.repository.CategoryRepository;
import com.example.pethappy.repository.PictureRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.ProductService;
import com.example.pethappy.validation.AddProductBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productsRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productsRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<Product> findByCategoryName(CategoryNameEnum nameEnum) {

        Category category = categoryRepository.findByCategoryName(nameEnum);

        return productsRepository.getByCategory(category);
    }

    @Override
    public List<ProductExportDto> findByPetTypeEnum(PetTypeEnum petTypeEnum) {

        return productsRepository.getByForType(petTypeEnum)
                .stream().
                map(product -> modelMapper.map(product, ProductExportDto.class))
                .collect(Collectors.toList());
    }



    @Override
    public ProductExportDto getProductDtoById(Long id) {
        Product product = productsRepository.findById(id).get();

        ProductExportDto exportDto = modelMapper.map(product, ProductExportDto.class);
        exportDto.setPictureId(product.getPicture().getId());
        return exportDto;
    }

    @Override
    public Product addProduct(AddProductBindingModel addProductBindingModel, Picture picture) {


        Product product = modelMapper.map(addProductBindingModel, Product.class);
        product.setStorageCount(product.getStorageCount());
        product.setForType(PetTypeEnum.valueOf(addProductBindingModel.getForType()));

        product.setPicture(picture);

        productsRepository.save(product);

        return product;
    }

    @Override
    public Product findProductById(Long id) {
        return productsRepository.findById(id).get();
    }

    @Override
    public double sumFinalPrice(List<ProductExportDto> productList) {

        double finalPrice = 0;

        for (ProductExportDto exportDto : productList) {
            finalPrice += exportDto.getPrice().doubleValue() * exportDto.getCount();
        }

        return Math.floor(finalPrice * 100)/100.00;

    }

    @Override
    public void deleteCurrentProduct(Long id) {
        productsRepository.deleteById(id);
    }

    @Override
    public void updateProductStorageCount(Product product, int amount) {
        product.setStorageCount(product.getStorageCount() + amount);

        productsRepository.save(product);
    }
}
