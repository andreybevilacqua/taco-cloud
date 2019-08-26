package com.ab.taco.service.message;

import com.ab.taco.model.Order;
import com.ab.taco.service.OrderMessagingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {

    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue-name}")
    private String queueName;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        MessageConverter converter = rabbitTemplate.getMessageConverter();

        MessageProperties properties = new MessageProperties();
        properties.setHeader("X_ORDER_SOURCE", "WEB");

        Message message = converter.toMessage(order, properties);
        rabbitTemplate.send(queueName, message);
    }

    // You can use direct the convertAndSend() method.
    public void sendOrderWithConverter(Order order) {
        rabbitTemplate.convertAndSend(queueName, order, (message) -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }

}
