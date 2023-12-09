package com.example.pethappy.service;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Category;
import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.model.entity.enums.CategoryNameEnum;
import com.example.pethappy.repository.CategoryRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.impl.ProductServiceImpl;
import com.example.pethappy.validation.AddProductBindingModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productsRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private  ModelMapper modelMapper;

    private ProductServiceImpl toTest;

    @BeforeEach
    public void setUp() {
        toTest = new ProductServiceImpl(productsRepository, categoryRepository, modelMapper);
    }


    @Test
    public void testFindByCategoryName() {

        //Arrange
        List<Product> products = new ArrayList<>();
        Category category = Mockito.mock(Category.class);
        CategoryNameEnum nameEnum = Mockito.mock(CategoryNameEnum.class);

        Mockito.when(categoryRepository.findByCategoryName(nameEnum)).thenReturn(category);
        Mockito.when(productsRepository.getByCategory(category)).thenReturn(products);

        //Act
        List<Product> productsList = toTest.findByCategoryName(nameEnum);

        //Assert
        Assertions.assertEquals(productsList, products);
    }

    @Test
    public void testGetProductDtoById() {

        //Arrange
        Product product = Mockito.mock(Product.class);
        ProductExportDto exportDto = Mockito.mock(ProductExportDto.class);
        long id = anyLong();

        Mockito.when(productsRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(modelMapper.map(product, ProductExportDto.class)).thenReturn(exportDto);
        Mockito.when(product.getPicture()).thenReturn(Mockito.mock(Picture.class));

        //Act
        ProductExportDto dto = toTest.getProductDtoById(id);

        //Assert
        Assertions.assertEquals(dto, exportDto);

    }

    @Test
    public void testAddProduct() {

        //Arrange
        Product product = Mockito.mock(Product.class);
        AddProductBindingModel addProductBindingModel = Mockito.mock(AddProductBindingModel.class);
        Picture picture = Mockito.mock(Picture.class);

        Mockito.when(modelMapper.map(addProductBindingModel, Product.class)).thenReturn(product);
        Mockito.when(addProductBindingModel.getForType()).thenReturn("DOG");


        //Act
        toTest.addProduct(addProductBindingModel, picture);

        //Assert
        Mockito.verify(productsRepository).save(product);
    }

    @Test
    public void testSumFinalPrice() {

        //Arrange
        List<ProductExportDto> productExportDtoList = new ArrayList<>();
        ProductExportDto exportDto = new ProductExportDto();
        exportDto.setPrice(new BigDecimal(15.50));
        exportDto.setCount(3);
        ProductExportDto exportDto1 =new ProductExportDto();
        exportDto1.setPrice(new BigDecimal(10.10));
        exportDto1.setCount(5);

        productExportDtoList.add(exportDto);
        productExportDtoList.add(exportDto1);


        //Act
        double sum = toTest.sumFinalPrice(productExportDtoList);

        //Assert
        Assertions.assertEquals(97, sum);

    }
}
