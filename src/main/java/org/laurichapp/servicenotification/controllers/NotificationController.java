package org.laurichapp.servicenotification.controllers;

import jakarta.validation.Valid;
import org.laurichapp.servicenotification.services.EmailService;
import org.laurichapp.servicenotification.models.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService facadeNotifications) {
        this.emailService = facadeNotifications;
    }

    @PostMapping("/envoi-email")
    public ResponseEntity<String> envoiEmail(@RequestBody @Valid Email email) {
        try{
            this.emailService.envoyerEmail(email, Optional.empty(), Optional.empty());
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail classique a bien été envoyé à " + email.getDestinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }

    @PostMapping("/envoi-email-generique")
    public ResponseEntity<String> envoiEmailGenerique(@RequestBody @Valid Email email) {
        try{
            this.emailService.envoyerEmail(email, Optional.of("bienvenue"), Optional.empty());
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail générique a bien été envoyé à " + email.getDestinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }

    @PostMapping("/envoi-email-pieceJointe")
    public ResponseEntity<String> envoiEmailAvecPieceJointe(@RequestBody @Valid Email email) {
        try{
            this.emailService.envoyerEmail(email, Optional.empty(), Optional.of("/logo/Fond_Noir_V2.png"));
            return ResponseEntity.status(HttpStatus.CREATED).body("E-mail avec la pièce jointe a bien été envoyé à " + email.getDestinataire());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi : " + e.getMessage());
        }
    }
}
