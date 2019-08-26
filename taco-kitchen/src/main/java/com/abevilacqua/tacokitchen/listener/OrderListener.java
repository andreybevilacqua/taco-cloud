package com.abevilacqua.tacokitchen.listener;

import com.abevilacqua.tacokitchen.model.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveOrder(Order order){
        System.out.println("Order received");
    }
}
