package com.example.pethappy.service.impl;

import com.example.pethappy.model.dto.OrderExportDto;
import com.example.pethappy.model.dto.ProductExportDto;
import com.example.pethappy.model.entity.Order;
import com.example.pethappy.model.entity.OrderedProduct;
import com.example.pethappy.model.entity.Owner;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.repository.OrderRepository;
import com.example.pethappy.repository.OrderedProductRepository;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.OrderService;
import com.example.pethappy.util.Cart;
import com.example.pethappy.validation.OrderBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final Cart cart;
    private final ModelMapper modelMapper;
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;
    private final OwnerRepository ownerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(Cart cart, ModelMapper modelMapper, OrderedProductRepository orderedProductRepository, OrderRepository orderRepository, OwnerRepository ownerRepository, ProductRepository productRepository) {
        this.cart = cart;
        this.modelMapper = modelMapper;
        this.orderedProductRepository = orderedProductRepository;
        this.orderRepository = orderRepository;
        this.ownerRepository = ownerRepository;
        this.productRepository = productRepository;
    }


    @Override
    public void addNewOrder(OrderBindingModel orderBindingModel, String username) {

        List<OrderedProduct> orderedProductList = new ArrayList<>();

        for (ProductExportDto productExportDto : cart.getProducts()){
            OrderedProduct orderedProduct = modelMapper.map(productExportDto, OrderedProduct.class);
            orderedProduct.setProductId(productExportDto.getId());

            orderedProductList.add(orderedProduct);

            Product product = productRepository.findById(productExportDto.getId()).get();

            if (product.getStorageCount() == 0) {
                productRepository.delete(product);
            }

        }

        orderedProductRepository.saveAll(orderedProductList);
        Owner owner = ownerRepository.findByUsername(username);

        Order order = modelMapper.map(orderBindingModel, Order.class);
        order.setOwner(owner);
        order.setCreatedOn(LocalDateTime.now());
        order.setOrderedProducts(orderedProductList);
        orderRepository.save(order);
        cart.getProducts().clear();
    }

    @Override
    public void clearOrder() {

        for (Order order : orderRepository.findAll()) {
            if (order.getCreatedOn().isAfter(order.getCreatedOn().plusDays(7))) {
                orderRepository.delete(order);
            }
        }
    }

    @Override
    public List<OrderExportDto> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderExportDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteCurrentOrder(Long id) {
        orderRepository.deleteById(id);
    }


}
