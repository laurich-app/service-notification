package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.dtos.rabbitmq.EmailDTO;
import org.laurichapp.servicenotification.dtos.rabbitmq.GenererCommandeDTO;
import org.laurichapp.servicenotification.exceptions.EmailException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ServiceRabbitMQListener implements RabbitListenerConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRabbitMQListener.class);
    private final EmailService emailService;

    public ServiceRabbitMQListener( @Autowired EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.inscription.bienvenue}")
    public void consumeInscription(EmailDTO emailDTO) {
        LOGGER.info("Envoi email bienvenue envoyé : {}", emailDTO);
        try {
            emailService.envoyerEmailBienvenu(emailDTO);
        } catch (EmailException e) {
            LOGGER.error("Le mail n'a pas pus être envoyé pour l'inscription : {}", emailDTO);
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.generer.commande}")
    public void consumeCommande(GenererCommandeDTO g) {
        LOGGER.info("Envoi email de confirmation de commande : {}", g);
        EmailDTO e = new EmailDTO(g.email(), "");
        try {
            emailService.envoyerEmailConfirmCommande(e, g.commande());
        } catch (EmailException ex) {
            LOGGER.error("Le mail n'a pas pus être envoyé pour la commande : {}", g);
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        // NOP
    }
}
