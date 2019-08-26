package com.abevilacqua.tacokitchen.config;

import com.abevilacqua.tacokitchen.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Destination;
import java.util.HashMap;

@Configuration
@EnableJms
public class JmsMessageConfig {

    @Value("${spring.artemis.host}")
    private String brokerUrl;

    @Value("${spring.jms.template.default-destination}")
    private String orderQueue;

    @Value("${spring.artemis.user}")
    private String user;

    @Value("${spring.artemis.password}")
    private String password;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setUser(user);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jms = new JmsTemplate(receiverActiveMQConnectionFactory());
//        jms.setMessageConverter(messageConverter(jacksonObjectMapper));
        return jms;
    }

    @Bean
    public Destination orderQueue() {return new ActiveMQQueue(orderQueue); }

//    @Bean
//    public MessageConverter messageConverter(ObjectMapper objectMapper){
//        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
//        messageConverter.setObjectMapper(objectMapper);
//        messageConverter.setTargetType(MessageType.TEXT);
//
//        messageConverter.setTypeIdPropertyName("_typeId");
//
//        //now set idMappings for serialization/deserialization
//        HashMap<String, Class<?>> idMapping = new HashMap<>();
//        idMapping.put(Order.class.getName(), Order.class);
//        messageConverter.setTypeIdMappings(idMapping);
//
//        return messageConverter;
//    }

}
