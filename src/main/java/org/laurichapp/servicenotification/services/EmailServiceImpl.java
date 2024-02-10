package org.laurichapp.servicenotification.services;

import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.laurichapp.servicenotification.dtos.EmailDTO;
import org.laurichapp.servicenotification.models.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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
    public void envoyerEmail(EmailDTO emailDTO, Optional<String> nomTemplate) {
        try {
            Email email = Email.fromDTO(emailDTO);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            String cheminPieceJointe = email.getCheminPieceJointe();
            if(cheminPieceJointe != null && (!cheminPieceJointe.isBlank())){
                    String nomFichierPieceJointe =new ClassPathResource(cheminPieceJointe).getFilename();
                    if (nomFichierPieceJointe != null) {
                        helper.addAttachment(nomFichierPieceJointe, new ClassPathResource(cheminPieceJointe).getFile());
                    }
            }
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
