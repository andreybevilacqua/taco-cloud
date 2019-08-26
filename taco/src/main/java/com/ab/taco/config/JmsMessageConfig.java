package com.ab.taco.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

@Configuration
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
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        connectionFactory.setUser(user);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(senderActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jms = new JmsTemplate(cachingConnectionFactory());
//        jms.setMessageConverter(messageConverter(jacksonObjectMapper));
        return jms;
    }

    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue(orderQueue);
    }

    // The RabbitMQConfig is setting a Bean MessageConverter as well.
    // You can remove that one and uncomment this MessageConverter bean.
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
