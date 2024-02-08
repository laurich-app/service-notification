package org.laurichapp.servicenotification.models;

import java.time.LocalDateTime;

public class Email {
    private String destinataire;
    private String objet;
    private String contenu;
    private LocalDateTime date;

    public Email( String destinataire, String objet, String contenu) {
        this.destinataire = destinataire;
        this.objet = objet;
        this.contenu = contenu;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getDate() {
        return date;
    }

}
