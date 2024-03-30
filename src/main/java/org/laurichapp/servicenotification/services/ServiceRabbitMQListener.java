package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.dtos.CommandeDTO;
import org.laurichapp.servicenotification.dtos.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class ServiceRabbitMQListener implements RabbitListenerConfigurer {
    private static final Logger LOGGER = Logger.getLogger(ServiceRabbitMQListener.class.getName());
    private final EmailService emailService;

    public ServiceRabbitMQListener( @Autowired EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.inscription.bienvenue}")
    public void consumeInscription(EmailDTO emailDTO) {
        LOGGER.info("Envoi email bienvenue envoyé : " + emailDTO);
        emailService.envoyerEmailBienvenu(emailDTO);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.generer.commande}")
    public void consumeCommande(EmailDTO emailDTO, CommandeDTO commandeDTO) {
        LOGGER.info("Envoi email de confirmation de commande : " + commandeDTO);
        emailService.envoyerEmailConfirmCommande(emailDTO, commandeDTO);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
