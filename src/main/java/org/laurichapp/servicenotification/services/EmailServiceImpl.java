package org.laurichapp.servicenotification.services;

import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.laurichapp.servicenotification.dtos.EmailDTO;
import org.laurichapp.servicenotification.models.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String emetteur;

    public EmailServiceImpl(Configuration configuration, JavaMailSenderImpl javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void envoyerEmailBienvenu(EmailDTO emailDTO) {
        Email email = Email.fromDTO(emailDTO);
        envoyerEmailAvecTemplate(email, "bienvenue.ftl", null);
    }

    @Override
    public void envoyerEmailBienvenuPJ(EmailDTO emailDTO) {
        Email email = Email.fromDTO(emailDTO);
        envoyerEmailAvecTemplate(email,"bienvenue.ftl", email.getCheminPieceJointe());
    }

    @Override
    public void envoyerEmailConfirmCommande(EmailDTO emailDTO) {
        Email email = Email.fromDTO(emailDTO);
        envoyerEmailAvecTemplate(email,"commande.ftl", null);
    }

    @Override
    public void envoyerEmailConfirmCommandePJ(EmailDTO emailDTO) {
        Email email = Email.fromDTO(emailDTO);
        envoyerEmailAvecTemplate(email,"commande.ftl", email.getCheminPieceJointe());
    }

    private void envoyerEmailAvecTemplate(Email email, String templateName, String cheminPieceJointe) throws RuntimeException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            ajouterPieceJointe(helper, cheminPieceJointe);
            if(templateName != null) {
                Template template = configuration.getTemplate(templateName);
                String emailHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template, email);
                helper.setText(emailHtml, true);
            }else{
                helper.setText(email.getContenu());
            }
            helper.setFrom(emetteur);
            helper.setTo(email.getDestinataire());
            helper.setSubject(email.getObjet());
            javaMailSender.send(message);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }

    public void ajouterPieceJointe (MimeMessageHelper helper, String cheminPieceJointe) throws RuntimeException {
        if (cheminPieceJointe != null && !cheminPieceJointe.isBlank()) {
            String nomFichierPieceJointe = new ClassPathResource(cheminPieceJointe).getFilename();
            if (nomFichierPieceJointe != null) {
                try {
                    helper.addAttachment(nomFichierPieceJointe, new ClassPathResource(cheminPieceJointe).getFile());
                } catch (MessagingException | IOException e) {
                    throw new RuntimeException("Erreur lors de l'ajout de la pièce jointe", e);
                }
            }
        }
    }

}
