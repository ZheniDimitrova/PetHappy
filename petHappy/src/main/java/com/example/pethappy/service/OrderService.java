package com.example.pethappy.service;

import com.example.pethappy.validation.OrderBindingModel;

public interface OrderService {
    void addNewOrder(OrderBindingModel orderBindingModel, String username);

    void clearOrder();

}
