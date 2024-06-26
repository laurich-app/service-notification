package org.laurichapp.servicenotification.services;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicenotification.dtos.rabbitmq.CommandeDTO;
import org.laurichapp.servicenotification.dtos.rabbitmq.EmailDTO;
import org.laurichapp.servicenotification.dtos.rabbitmq.GenererCommandeDTO;
import org.laurichapp.servicenotification.exceptions.EmailException;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.laurichapp.servicenotification.models.Notification;
import org.laurichapp.servicenotification.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class TestServiceRabbitMQListener {

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private EmailService emailService;

    private final ServiceRabbitMQListener serviceRabbitMQListener;

    @MockBean
    private Notification notificationToReturn;

    @MockBean
    private JwtDecoder jwtDecoder;

    @BeforeEach
    public void setUp() {
        doReturn(notificationToReturn).when(this.notificationRepository).insert(any(Notification.class));
        doReturn(notificationToReturn).when(this.notificationRepository).save(any(Notification.class));
        ObjectId objectId = new ObjectId();
        doReturn(objectId).when(notificationToReturn).getIdNotification();
        doReturn(Optional.of(notificationToReturn)).when(this.notificationRepository).findById(objectId.toString());
    }

    public TestServiceRabbitMQListener(@Autowired ServiceRabbitMQListener serviceRabbitMQListener) {
        this.serviceRabbitMQListener = serviceRabbitMQListener;
    }

    @Test
    void testConsumeInscription() throws EmailException, NotificationNotFoundException {
        // BEGIN
        EmailDTO emailDTO = mock(EmailDTO.class);

        // WHEN
        this.serviceRabbitMQListener.consumeInscription(emailDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailBienvenu(emailDTO);
    }

    @Test
    void testConsumeInscriptionFailLogged() throws EmailException, NotificationNotFoundException {
        // BEGIN
        EmailDTO emailDTO = mock(EmailDTO.class);
        doThrow(EmailException.class).when(this.emailService).envoyerEmailBienvenu(emailDTO);

        // WHEN
        this.serviceRabbitMQListener.consumeInscription(emailDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailBienvenu(emailDTO);
    }

    @Test
    void testConsumeCommande() throws EmailException, NotificationNotFoundException {
        // BEGIN
        CommandeDTO commandeDTO = mock(CommandeDTO.class);
        GenererCommandeDTO genererCommandeDTO = new GenererCommandeDTO("email", commandeDTO);

        // WHEN
        this.serviceRabbitMQListener.consumeCommande(genererCommandeDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailConfirmCommande(any(EmailDTO.class), eq(commandeDTO));
    }

    @Test
    void testConsumeCommandeFailLogged() throws EmailException, NotificationNotFoundException {
        // BEGIN
        CommandeDTO commandeDTO = mock(CommandeDTO.class);
        GenererCommandeDTO genererCommandeDTO = new GenererCommandeDTO("email", commandeDTO);
        doThrow(EmailException.class).when(this.emailService).envoyerEmailConfirmCommande(any(EmailDTO.class), eq(commandeDTO));

        // WHEN
        this.serviceRabbitMQListener.consumeCommande(genererCommandeDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailConfirmCommande(any(EmailDTO.class), eq(commandeDTO));
    }

}
