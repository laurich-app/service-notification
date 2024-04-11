package org.laurichapp.servicenotification.services;

import freemarker.template.*;
import jakarta.mail.internet.MimeMessage;
import org.laurichapp.servicenotification.dtos.rabbitMQ.CommandeDTO;
import org.laurichapp.servicenotification.dtos.rabbitMQ.EmailDTO;
import org.laurichapp.servicenotification.exceptions.EmailException;
import org.laurichapp.servicenotification.models.Commande;
import org.laurichapp.servicenotification.models.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String emetteur;

    @Value("${client.url}")
    private String clientUrl;

    public EmailServiceImpl(Configuration configuration, JavaMailSenderImpl javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void envoyerEmailBienvenu(EmailDTO emailDTO) throws EmailException {
        Email email = Email.fromDTO(emailDTO);
        email.setObjet("Bienvenue chez Laurich'App");
        envoyerEmailAvecTemplate(email, "bienvenue.ftl", null);
    }

    @Override
    public void envoyerEmailConfirmCommande(EmailDTO emailDTO, CommandeDTO commandeDTO) throws EmailException {
        Email email = Email.fromDTO(emailDTO);
        Commande commande = Commande.fromDTO(commandeDTO);
        email.setObjet("Confirmation de commande");
        envoyerEmailAvecTemplate(email,"commande.ftl", commande);
    }

    private void envoyerEmailAvecTemplate(Email email, String templateName, Commande commande) throws EmailException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate(templateName);
            Map<String, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("pseudo", email.getPseudoDestinataire());
            model.put("client_url", clientUrl);
            if(commande != null){
                model.put("commande", commande);
            }
            String emailHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(emailHtml, true);
            helper.setFrom(emetteur);
            helper.setTo(email.getDestinataire());
            helper.setSubject(email.getObjet());
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }

}
