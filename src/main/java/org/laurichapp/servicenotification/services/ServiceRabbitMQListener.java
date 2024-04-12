package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.dtos.rabbitmq.EmailDTO;
import org.laurichapp.servicenotification.dtos.rabbitmq.GenererCommandeDTO;
import org.laurichapp.servicenotification.enums.NotificationEtat;
import org.laurichapp.servicenotification.enums.NotificationFonction;
import org.laurichapp.servicenotification.exceptions.EmailException;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.laurichapp.servicenotification.facades.FacadeNotification;
import org.laurichapp.servicenotification.models.Notification;
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
    private final FacadeNotification facadeNotification;

    public ServiceRabbitMQListener( @Autowired EmailService emailService, @Autowired FacadeNotification facadeNotification) {
        this.emailService = emailService;
        this.facadeNotification = facadeNotification;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.inscription.bienvenue}")
    public void consumeInscription(EmailDTO emailDTO) throws NotificationNotFoundException {
        LOGGER.info("Envoi email bienvenue envoyé : {}", emailDTO);
        Notification notification = facadeNotification.creerNotification(emailDTO.email(), emailDTO.pseudo(), NotificationFonction.NOTIFIER_USER_INSCRIPTION);
        try {
            emailService.envoyerEmailBienvenu(emailDTO);
            facadeNotification.majNotificationEtat(notification.getIdNotification().toString(), NotificationEtat.SUCCES);
        } catch (EmailException e ) {
            facadeNotification.majNotificationEtat(notification.getIdNotification().toString(), NotificationEtat.ECHEC);
            LOGGER.error("Le mail n'a pas pus être envoyé pour l'inscription : {}", emailDTO);
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.generer.commande}")
    public void consumeCommande(GenererCommandeDTO g) throws NotificationNotFoundException {
        LOGGER.info("Envoi email de confirmation de commande : {}", g);
        EmailDTO e = new EmailDTO(g.email(), "");
        Notification notification = facadeNotification.creerNotification(e.email(), e.pseudo(), NotificationFonction.NOTIFIER_USER_COMMANDE);
        try {
            emailService.envoyerEmailConfirmCommande(e, g.commande());
            facadeNotification.majNotificationEtat(notification.getIdNotification().toString(), NotificationEtat.SUCCES);
        } catch (EmailException ex) {
            facadeNotification.majNotificationEtat(notification.getIdNotification().toString(), NotificationEtat.ECHEC);
            LOGGER.error("Le mail n'a pas pus être envoyé pour la commande : {}", g);
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        // NOP
    }
}
