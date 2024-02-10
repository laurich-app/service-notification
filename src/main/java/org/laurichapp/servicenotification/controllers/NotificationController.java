package org.laurichapp.servicenotification.controllers;

import jakarta.validation.Valid;
import org.laurichapp.servicenotification.dtos.EmailDTO;
import org.laurichapp.servicenotification.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/envoi-email")
    public ResponseEntity<String> envoiEmail(@RequestBody @Valid EmailDTO emailDTO) {
        try{
            this.emailService.envoyerEmail(emailDTO, Optional.empty());
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail classique a bien été envoyé à " + emailDTO.destinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }

    @PostMapping("/envoi-email-generique")
    public ResponseEntity<String> envoiEmailGenerique(@RequestBody @Valid EmailDTO emailDTO) {
        try{
            this.emailService.envoyerEmail(emailDTO, Optional.of("bienvenue"));
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail générique a bien été envoyé à " + emailDTO.destinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }
}
