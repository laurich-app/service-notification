package org.laurichapp.servicenotification.facades;

import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.dtos.pagination.Paginate;
import org.laurichapp.servicenotification.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicenotification.enums.NotificationEtat;
import org.laurichapp.servicenotification.enums.NotificationFonction;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.laurichapp.servicenotification.models.Notification;

public interface FacadeNotification {

    /**
     * Créer une notification à partir d'un email pour l'enregistrement dans la base de données.
     * @param emailClient
     * @param pseudoClient
     * @param notificationFonction
     */
    Notification creerNotification(String emailClient, String pseudoClient, NotificationFonction notificationFonction);

    /**
     * Mise à jour de l'etat de la notification (EN_ATTENTE, SUCCES, ECHEC)
     * @param idNotification
     * @param nouvelleNotificationEtat
     * @throws NotificationNotFoundException
     */
    void majNotificationEtat(String idNotification, NotificationEtat nouvelleNotificationEtat) throws NotificationNotFoundException;

    /**
     * Récupère la liste des notifications pagines.
     * @param paginateRequestDTO
     * @return Liste de notification
     */
    Paginate<NotificationOutDTO> getAllNotifications(PaginateRequestDTO paginateRequestDTO);

    /**
     * Récupère une NotificationDTO.
     * @param idNotification
     * @return
     * @throws NotificationNotFoundException
     */
    NotificationOutDTO getNotificationDTOById(String idNotification) throws NotificationNotFoundException;

    /**
     * Récupère une Notification.
     * @param idNotification
     * @return
     * @throws NotificationNotFoundException
     */
    Notification getNotificationById(String idNotification) throws NotificationNotFoundException;
}
