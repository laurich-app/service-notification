package org.laurichapp.servicenotification.models;

import org.laurichapp.servicenotification.dtos.EmailDTO;

import java.time.LocalDateTime;

public class Email {
    private String destinataire;
    private String objet;
    private String contenu;
    private LocalDateTime date;
    private String cheminPieceJointe;

    public Email( String destinataire, String objet, String contenu, String cheminPieceJointe) {
        this.destinataire = destinataire;
        this.objet = objet;
        this.contenu = contenu;
        this.cheminPieceJointe = cheminPieceJointe;
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

    public String getContenu() {
        return contenu;
    }

    public String getCheminPieceJointe() {
        return cheminPieceJointe;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCheminPieceJointe(String cheminPieceJointe) {
        this.cheminPieceJointe = cheminPieceJointe;
    }

    public static Email fromDTO(EmailDTO emailDTO) {
        Email email = new Email();
        email.setDestinataire(emailDTO.destinataire());
        email.setObjet(emailDTO.objet());
        email.setContenu(emailDTO.contenu());
        email.setCheminPieceJointe(emailDTO.cheminPieceJointe());
        return email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "destinataire='" + destinataire + '\'' +
                ", objet='" + objet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                ", cheminPieceJointe='" + cheminPieceJointe + '\'' +
                '}';
    }
}
