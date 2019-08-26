package com.ab.taco.service;

import com.ab.taco.model.Order;

public interface OrderMessagingService {

    void sendOrder(Order order);
}
