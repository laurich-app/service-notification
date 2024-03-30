package org.laurichapp.servicenotification.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigMailBienvenue {
    @Value("${spring.rabbitmq.queue.notification.inscription.bienvenue}")
    private String queue;
    @Value("${spring.rabbitmq.exchange.notification.inscription.bienvenue}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey.notification.inscription.bienvenue}")
    private String routingKey;

    @Bean
    public Queue queueBienvenue() {
        return new Queue(queue);
    }

    @Bean
    public Exchange exchangeBienvenue(){
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Binding bindingBienvenue(){
        return BindingBuilder.bind(queueBienvenue())
                .to(exchangeBienvenue())
                .with(routingKey)
                .noargs();
    }

}
