package com.example.pethappy.scheduling;

import com.example.pethappy.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearOrders {

   private final OrderService orderService;

    public ClearOrders(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void clearOrder() {

        orderService.clearOrder();
    }
}
