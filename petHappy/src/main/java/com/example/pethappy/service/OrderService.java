package com.example.pethappy.service;

import com.example.pethappy.model.dto.OrderExportDto;
import com.example.pethappy.validation.OrderBindingModel;

import java.util.List;

public interface OrderService {
    void addNewOrder(OrderBindingModel orderBindingModel, String username);

    void clearOrder();

    List<OrderExportDto> getAllOrders();
}
