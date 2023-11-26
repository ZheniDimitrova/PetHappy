package com.example.pethappy.service;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import com.example.pethappy.validation.AddProductBindingModel;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ProductService {

    List<Product> findByCategoryName(CategoryNameEnum nameEnum);

    List<ProductExportDto> findByPetTypeEnum(PetTypeEnum petTypeEnum);

    void initProducts();

    ProductExportDto getProductDtoById(Long id);

    Product addProduct(AddProductBindingModel addProductBindingModel) throws IOException;


    Product findProductById(Long id);

    double sumFinalPrice(List<ProductExportDto> productList) throws ParseException;
}

