package org.laurichapp.servicenotification.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigMailCommande {
    @Value("${spring.rabbitmq.queue.notification.generer.commande}")
    private String queue;
    @Value("${spring.rabbitmq.exchange.notification.generer.commande}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey.notification.generer.commande}")
    private String routingKey;

    @Bean
    public Queue queueCommande() {
        return new Queue(queue);
    }

    @Bean
    public Exchange exchangeCommande(){
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Binding bindingCommande(){
        return BindingBuilder.bind(queueCommande())
                .to(exchangeCommande())
                .with(routingKey)
                .noargs();
    }

}
