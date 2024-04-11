package org.laurichapp.servicenotification.facades;

import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.dtos.pagination.Paginate;
import org.laurichapp.servicenotification.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FacadeNotificationImpl implements FacadeNotification{
    @Override
    public Paginate<NotificationOutDTO> getAllNotifications(PaginateRequestDTO paginateRequestDTO) {
        return null;
    }

    @Override
    public NotificationOutDTO getNotificationById(String idNotification) throws NotificationNotFoundException {
        return null;
    }
}
