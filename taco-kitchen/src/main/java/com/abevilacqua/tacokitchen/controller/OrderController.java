package com.abevilacqua.tacokitchen.controller;

import com.abevilacqua.tacokitchen.model.Order;
import com.abevilacqua.tacokitchen.service.OrderReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderReceiver orderReceiver;

    @GetMapping("/receive")
    public String receiveOrder() {
        Order result = orderReceiver.receiveOrder();
        if(result != null) {
            return "order received";
        }
        return "no order";
    }
}
