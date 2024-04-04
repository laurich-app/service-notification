package org.laurichapp.servicenotification.models;

import org.laurichapp.servicenotification.dtos.EmailDTO;

public class Email {
    private String destinataire;
    private String objet;
    private String pseudoDestinataire;

    public String getDestinataire() {
        return destinataire;
    }

    public String getObjet() {
        return objet;
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
                ", pseudoDestinataire='" + pseudoDestinataire + '\'' +
                '}';
    }
}
