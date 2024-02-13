package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.dtos.EmailDTO;

public interface EmailService {

    /**
     *  Envoi d'un mail générique de bienvenu selon le template bienvenue.ftl, sans pièce jointe
     * @param emailDTO
     */
    void envoyerEmailBienvenu(EmailDTO emailDTO);

    /**
     *  Envoi d'un mail générique de bienvenu selon le template bienvenue.ftl, avec pièce jointe
     * @param emailDTO
     */
    void envoyerEmailBienvenuPJ(EmailDTO emailDTO);

    /**
     *  Envoi d'un mail générique de confirmationCommande selon le template commande.ftl, sans pièce jointe
     * @param emailDTO
     */
    void envoyerEmailConfirmCommande(EmailDTO emailDTO);

    /**
     *  Envoi d'un mail générique de confirmationCommande selon le template commande.ftl, avec pièce jointe
     * @param emailDTO
     */
    void envoyerEmailConfirmCommandePJ(EmailDTO emailDTO);
}
