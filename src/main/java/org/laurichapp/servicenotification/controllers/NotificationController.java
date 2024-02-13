package org.laurichapp.servicenotification.controllers;

import jakarta.validation.Valid;
import org.laurichapp.servicenotification.dtos.EmailDTO;
import org.laurichapp.servicenotification.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email-bienvenu")
    public ResponseEntity<String> envoiEmailBienvenu(@RequestBody @Valid EmailDTO emailDTO) {
        try{
            this.emailService.envoyerEmailBienvenu(emailDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail classique a bien été envoyé à " + emailDTO.destinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }

    @PostMapping("/email-confirm-commande")
    public ResponseEntity<String> envoiEmailConfirmationCommande(@RequestBody @Valid EmailDTO emailDTO) {
        try{
            this.emailService.envoyerEmailConfirmCommandePJ(emailDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail classique a bien été envoyé à " + emailDTO.destinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }


}
