package com.example.pethappy.service;

import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Order;
import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.repository.OrderRepository;
import com.example.pethappy.repository.OrderedProductRepository;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.impl.OrderServiceImpl;
import com.example.pethappy.util.Cart;
import com.example.pethappy.validation.OrderBindingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private Cart cart;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private OrderedProductRepository orderedProductRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private ProductRepository productRepository;

    private OrderServiceImpl toTest;

    @BeforeEach
    public void setUp() {
        toTest = new OrderServiceImpl(cart, modelMapper, orderedProductRepository, orderRepository, ownerRepository, productRepository);
    }

    @Test
    public void testAddNewOrder() {
        //Arrange
        List<ProductExportDto> productExportDtoS = new ArrayList<>();
        Owner owner = Mockito.mock(Owner.class);
        OrderBindingModel orderBindingModel = Mockito.mock(OrderBindingModel.class);
        Order order = Mockito.mock(Order.class);

        Mockito.when(cart.getProducts()).thenReturn(productExportDtoS);
        Mockito.when(ownerRepository.findByUsername("Pesho")).thenReturn(owner);
        Mockito.when(modelMapper.map(orderBindingModel, Order.class)).thenReturn(order);



        //Act

        toTest.addNewOrder(orderBindingModel, "Pesho");

        //Assert

        Mockito.verify(orderRepository).save(order);
    }

    @Test
    public void testClearOrder() {
        //Arrange
        Order order = new Order();
        order.setCreatedOn(LocalDateTime.now());
        Order order1 = new Order();
        order1.setCreatedOn(LocalDateTime.now());

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);

        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        //Act
        toTest.clearOrder();

        //Assert

        Mockito.verify(orderRepository, Mockito.times(0)).delete(any());
    }
}
