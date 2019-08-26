package com.abevilacqua.tacokitchen.service.impl;

import com.abevilacqua.tacokitchen.model.Order;
import com.abevilacqua.tacokitchen.service.OrderReceiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderReceiverService implements OrderReceiver {

    @Value("${spring.jms.template.default-destination}")
    private String orderQueue;

    private JmsTemplate jmsTemplate;

    public JmsOrderReceiverService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Order receiveOrder() {
        return (Order) jmsTemplate.receiveAndConvert(orderQueue);
    }
}
