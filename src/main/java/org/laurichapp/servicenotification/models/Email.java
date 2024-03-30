package org.laurichapp.servicenotification.models;

import org.laurichapp.servicenotification.dtos.EmailDTO;

import java.time.LocalDateTime;

public class Email {
    private String destinataire;
    private String objet;
    private LocalDateTime date;
    private String cheminPieceJointe;
    private String pseudoDestinataire;

    public Email(String destinataire, String objet, String cheminPieceJointe, String pseudoDestinataire) {
        this.destinataire = destinataire;
        this.objet = objet;
        this.cheminPieceJointe = cheminPieceJointe;
        this.pseudoDestinataire=pseudoDestinataire;
        this.date = LocalDateTime.now();
    }

    public Email() {
    }

    public String getDestinataire() {
        return destinataire;
    }

    public String getObjet() {
        return objet;
    }

    public String getCheminPieceJointe() {
        return cheminPieceJointe;
    }

    public String getPseudoDestinataire() {
        return pseudoDestinataire;
    }

    public void setPseudoDestinataire(String pseudoDestinataire) {
        this.pseudoDestinataire = pseudoDestinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCheminPieceJointe(String cheminPieceJointe) {
        this.cheminPieceJointe = cheminPieceJointe;
    }

    public static Email fromDTO(EmailDTO emailDTO) {
        Email email = new Email();
        email.setDestinataire(emailDTO.email());
        email.setPseudoDestinataire(emailDTO.pseudo());
        return email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "destinataire='" + destinataire + '\'' +
                ", objet='" + objet + '\'' +
                ", date=" + date +
                ", cheminPieceJointe='" + cheminPieceJointe + '\'' +
                ", pseudoDestinataire='" + pseudoDestinataire + '\'' +
                '}';
    }
}
