package org.laurichapp.servicenotification.services;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicenotification.dtos.rabbitMQ.CommandeDTO;
import org.laurichapp.servicenotification.dtos.rabbitMQ.EmailDTO;
import org.laurichapp.servicenotification.exceptions.EmailException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.Mockito.*;

@SpringBootTest
abstract class TestEmailService {
    private EmailService emailService;

    @SpyBean
    private JavaMailSenderImpl javaMailSender;

    public abstract EmailService getInstance();

    @BeforeEach
    void setUp() {
        this.emailService = this.getInstance();
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void testExceptionMailHandled() {
        // BEGIN
        EmailDTO emailDTO = new EmailDTO("email@email.com", "pseudo");
        doThrow(MailSendException.class).when(this.javaMailSender).send(any(MimeMessage.class));

        // WHEN
        Assertions.assertThrows(EmailException.class, () -> this.emailService.envoyerEmailBienvenu(emailDTO));
    }

    @Test
    void testEnvoyerEmailBienvenue() throws EmailException {
        // BEGIN
        EmailDTO emailDTO = new EmailDTO("email@email.com", "pseudo");

        // WHEN
        this.emailService.envoyerEmailBienvenu(emailDTO);

        // WHERE
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testEnvoyerEmailConfirmCommande() throws EmailException {
        // BEGIN
        EmailDTO emailDTO = new EmailDTO("email@email.com", "pseudo");
        CommandeDTO commandeDTO = mock(CommandeDTO.class);

        // WHEN
        this.emailService.envoyerEmailConfirmCommande(emailDTO, commandeDTO);

        // WHERE
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}
