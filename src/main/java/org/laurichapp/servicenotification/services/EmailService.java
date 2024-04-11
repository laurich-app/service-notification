package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.dtos.rabbitMQ.CommandeDTO;
import org.laurichapp.servicenotification.dtos.rabbitMQ.EmailDTO;
import org.laurichapp.servicenotification.exceptions.EmailException;

public interface EmailService {

    /**
     *  Envoi d'un mail générique de bienvenu selon le template bienvenue.ftl, sans pièce jointe
     * @param emailDTO
     */
    void envoyerEmailBienvenu(EmailDTO emailDTO) throws EmailException;

    /**
     *  Envoi d'un mail générique de confirmationCommande selon le template commande.ftl, sans pièce jointe
     * @param emailDTO
     * @param commandeDTO
     */
    void envoyerEmailConfirmCommande(EmailDTO emailDTO, CommandeDTO commandeDTO) throws EmailException;
}
