package org.laurichapp.servicenotification.facades;

import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.dtos.pagination.Paginate;
import org.laurichapp.servicenotification.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;

public interface FacadeNotification {
    /*========== GET ==========*/
    /**
     * Récupère la liste des notifications pagines.
     * @return Liste de notification
     */
    Paginate<NotificationOutDTO> getAllNotifications(PaginateRequestDTO paginateRequestDTO);

    /**
     * Récupère une Notification.
     * @return une commande
     */
    NotificationOutDTO getNotificationById(String idNotification) throws NotificationNotFoundException;
}
