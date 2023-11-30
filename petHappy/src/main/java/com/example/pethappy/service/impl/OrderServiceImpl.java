package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Order;
import com.example.pethappy.model.entity.OrderedProduct;
import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.repository.OrderRepository;
import com.example.pethappy.repository.OrderedProductRepository;
import com.example.pethappy.repository.OwnerRepository;
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
    private final OwnerRepository ownerRepository;

    public OrderServiceImpl(Cart cart, ModelMapper modelMapper, OrderedProductRepository orderedProductRepository, OrderRepository orderRepository, OwnerRepository ownerRepository) {
        this.cart = cart;
        this.modelMapper = modelMapper;
        this.orderedProductRepository = orderedProductRepository;
        this.orderRepository = orderRepository;
        this.ownerRepository = ownerRepository;
    }


    @Override
    public void addNewOrder(OrderBindingModel orderBindingModel, String username) {

        List<OrderedProduct> orderedProductList = cart.getProducts().stream()
                .map(productExportDto -> modelMapper.map(productExportDto, OrderedProduct.class))
                .collect(Collectors.toList());

        orderedProductRepository.saveAll(orderedProductList);
        Owner owner = ownerRepository.findByUsername(username);

        Order order = modelMapper.map(orderBindingModel, Order.class);
        order.setOwner(owner);
        orderRepository.save(order);
    }
}
