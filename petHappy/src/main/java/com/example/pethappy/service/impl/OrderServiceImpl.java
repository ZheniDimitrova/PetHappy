package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Order;
import com.example.pethappy.model.entity.OrderedProduct;
import com.example.pethappy.repository.OrderRepository;
import com.example.pethappy.repository.OrderedProductRepository;
import com.example.pethappy.service.OrderService;
import com.example.pethappy.util.Cart;
import com.example.pethappy.validation.OrderBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final Cart cart;
    private final ModelMapper modelMapper;
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(Cart cart, ModelMapper modelMapper, OrderedProductRepository orderedProductRepository, OrderRepository orderRepository) {
        this.cart = cart;
        this.modelMapper = modelMapper;
        this.orderedProductRepository = orderedProductRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public void addNewOrder(OrderBindingModel orderBindingModel) {

        List<OrderedProduct> orderedProductList = cart.getProducts().stream()
                .map(productExportDto -> modelMapper.map(productExportDto, OrderedProduct.class))
                .collect(Collectors.toList());

        orderedProductRepository.saveAll(orderedProductList);

        Order order = modelMapper.map(orderBindingModel, Order.class);
        orderRepository.save(order);
    }
}
