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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productsRepository;
    private final CategoryRepository categoryRepository;

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productsRepository, CategoryRepository categoryRepository, PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.productsRepository = productsRepository;
        this.categoryRepository = categoryRepository;
        this.pictureRepository = pictureRepository;
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

        for (int i = 1; i <= 36; i++) {
            Product product = new Product();
            product.setName("product" + i);
            product.setPrice(new BigDecimal(random.nextDouble()));

            PetTypeEnum petTypeEnum = null;
            if (i <= 12) {
                petTypeEnum = PetTypeEnum.CAT;
            } else if (i <= 24) {
                petTypeEnum = PetTypeEnum.DOG;
            } else {
                petTypeEnum = PetTypeEnum.OTHER;
            }
            product.setForType(petTypeEnum);
            product.setStorageCount(random.nextInt(9) + 1);
            productsRepository.save(product);
        }



    }

    @Override
    public ProductExportDto getProductDtoById(Long id) {
        Product product = productsRepository.findById(id).get();

        ProductExportDto exportDto = modelMapper.map(product, ProductExportDto.class);
        return exportDto;
    }

    @Override
    public Product addProduct(AddProductBindingModel addProductBindingModel) throws IOException {

        MultipartFile picture = addProductBindingModel.getPicture();

        Picture pic = new Picture(picture.getContentType(), picture.getName(), picture.getBytes());
        pictureRepository.save(pic);


        Product product = modelMapper.map(addProductBindingModel, Product.class);
        product.setStorageCount(product.getStorageCount());
        product.setForType(PetTypeEnum.valueOf(addProductBindingModel.getForType()));

        product.setPicture(pic);

        productsRepository.save(product);

        return product;
    }

    @Override
    public Product findProductById(Long id) {
        return productsRepository.findById(id).get();
    }

    @Override
    public double sumFinalPrice(List<ProductExportDto> productList) throws ParseException {

        double finalPrice = 0;

        for (ProductExportDto exportDto : productList) {
            finalPrice += exportDto.getPrice().doubleValue() * exportDto.getCount();
        }
//        DecimalFormat df = new DecimalFormat("#.##");
//        String f = df.format(finalPrice);
//
//        return Double.valueOf((Double) df.parse(f));
        return Math.floor(finalPrice * 100)/100.00;

    }
}
