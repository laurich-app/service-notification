package org.laurichapp.servicenotification.services;

import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.laurichapp.servicenotification.dtos.CommandeDTO;
import org.laurichapp.servicenotification.dtos.EmailDTO;
import org.laurichapp.servicenotification.models.Commande;
import org.laurichapp.servicenotification.models.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    @Value("${spring.mail.username}")
    private String emetteur;

    public EmailServiceImpl(Configuration configuration, JavaMailSender javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void envoyerEmailBienvenu(EmailDTO emailDTO) {
        Email email = Email.fromDTO(emailDTO);
        envoyerEmailAvecTemplate(email, "bienvenue.ftl", null, null);
    }

    @Override
    public void envoyerEmailBienvenuPJ(EmailDTO emailDTO) {
        Email email = Email.fromDTO(emailDTO);
        envoyerEmailAvecTemplate(email,"bienvenue.ftl", email.getCheminPieceJointe(), null);
    }

    @Override
    public void envoyerEmailConfirmCommande(EmailDTO emailDTO, CommandeDTO commandeDTO) {
        Email email = Email.fromDTO(emailDTO);
        Commande commande = Commande.fromDTO(commandeDTO);
        envoyerEmailAvecTemplate(email,"commande.ftl", null, commande);
    }

    @Override
    public void envoyerEmailConfirmCommandePJ(EmailDTO emailDTO, CommandeDTO commandeDTO) {
        Email email = Email.fromDTO(emailDTO);
        Commande commande = Commande.fromDTO(commandeDTO);
        envoyerEmailAvecTemplate(email,"commande.ftl", email.getCheminPieceJointe(), commande);
    }

    private void envoyerEmailAvecTemplate(Email email, String templateName, String cheminPieceJointe, Commande commande) throws RuntimeException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            ajouterPieceJointe(helper, cheminPieceJointe);
            if(templateName != null) {
                Template template = configuration.getTemplate(templateName);
                Map<String, Object> model = new HashMap<>();
                model.put("email", email);
                model.put("pseudo", email.getPseudoDestinataire());
                if(commande != null){
                    model.put("commande", commande);
                }
                String emailHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
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
