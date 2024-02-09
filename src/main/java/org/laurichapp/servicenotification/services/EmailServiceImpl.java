package org.laurichapp.servicenotification.services;

import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.laurichapp.servicenotification.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    @Value("${spring.mail.username}")
    private String emetteur;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void envoyerEmail(Email email, Optional<String> nomTemplate, Optional<String> cheminPieceJointe) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());


            cheminPieceJointe.ifPresent(chemin -> {
                try {
                    String nomFichierPieceJointe = new ClassPathResource(chemin).getFilename();
                    helper.addAttachment(nomFichierPieceJointe, new ClassPathResource(chemin).getFile());
                }catch (Exception e) {
                    throw new RuntimeException("Erreur lors de l'ajout de la pièce jointe", e);
                }
            });

            if(nomTemplate.isPresent()){
                Template template = configuration.getTemplate(nomTemplate.get() + ".ftl");
                String emailHtml = FreeMarkerTemplateUtils.processTemplateIntoString(template, email);
                helper.setText(emailHtml, true);
            }else{
                helper.setText(email.getContenu(), true);
            }

            helper.setFrom(emetteur);
            helper.setTo(email.getDestinataire());
            helper.setSubject(email.getObjet());
            javaMailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}
