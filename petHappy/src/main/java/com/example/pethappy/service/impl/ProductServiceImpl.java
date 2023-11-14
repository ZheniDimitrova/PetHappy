package com.example.pethappy.service.impl;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Category;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import com.example.pethappy.model.entity.enums.PetTypeEnum;
import com.example.pethappy.repository.CategoryRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public void initProducts() {
        if (productsRepository.count() > 0) {
            return;
        }

        Random random = new Random();

        for (int i = 1; i <= 21; i++) {
            Product product = new Product();
            product.setName("product" + i);
            product.setPrice(new BigDecimal(random.nextDouble()));

            PetTypeEnum petTypeEnum = null;
            if (i <= 7) {
                petTypeEnum = PetTypeEnum.CAT;
            } else if (i <= 14) {
                petTypeEnum = PetTypeEnum.DOG;
            } else {
                petTypeEnum = PetTypeEnum.OTHER;
            }
            product.setForType(petTypeEnum);
            productsRepository.save(product);
        }

    }
}
