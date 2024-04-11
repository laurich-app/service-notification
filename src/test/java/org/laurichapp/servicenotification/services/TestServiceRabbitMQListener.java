package org.laurichapp.servicenotification.services;

import org.junit.jupiter.api.Test;
import org.laurichapp.servicenotification.dtos.rabbitMQ.CommandeDTO;
import org.laurichapp.servicenotification.dtos.rabbitMQ.EmailDTO;
import org.laurichapp.servicenotification.dtos.rabbitMQ.GenererCommandeDTO;
import org.laurichapp.servicenotification.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class TestServiceRabbitMQListener {
    @MockBean
    private EmailService emailService;
    private final ServiceRabbitMQListener serviceRabbitMQListener;

    public TestServiceRabbitMQListener(@Autowired ServiceRabbitMQListener serviceRabbitMQListener) {
        this.serviceRabbitMQListener = serviceRabbitMQListener;
    }

    @Test
    void testConsumeInscription() throws EmailException {
        // BEGIN
        EmailDTO emailDTO = mock(EmailDTO.class);

        // WHEN
        this.serviceRabbitMQListener.consumeInscription(emailDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailBienvenu(emailDTO);
    }

    @Test
    void testConsumeInscriptionFailLogged() throws EmailException {
        // BEGIN
        EmailDTO emailDTO = mock(EmailDTO.class);
        doThrow(EmailException.class).when(this.emailService).envoyerEmailBienvenu(emailDTO);

        // WHEN
        this.serviceRabbitMQListener.consumeInscription(emailDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailBienvenu(emailDTO);
    }

    @Test
    void testConsumeCommande() throws EmailException {
        // BEGIN
        CommandeDTO commandeDTO = mock(CommandeDTO.class);
        GenererCommandeDTO genererCommandeDTO = new GenererCommandeDTO("email", commandeDTO);

        // WHEN
        this.serviceRabbitMQListener.consumeCommande(genererCommandeDTO);

        // WHERE
        verify(this.emailService, times(1)).envoyerEmailConfirmCommande(any(EmailDTO.class), eq(commandeDTO));
    }

    @Test
    void testConsumeCommandeFailLogged() throws EmailException {
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
