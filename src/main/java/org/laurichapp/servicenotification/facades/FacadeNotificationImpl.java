package org.laurichapp.servicenotification.facades;

import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.dtos.pagination.Paginate;
import org.laurichapp.servicenotification.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicenotification.dtos.pagination.Pagination;
import org.laurichapp.servicenotification.enums.NotificationEtat;
import org.laurichapp.servicenotification.enums.NotificationFonction;
import org.laurichapp.servicenotification.enums.NotificationType;
import org.laurichapp.servicenotification.exceptions.NotificationNotFoundException;
import org.laurichapp.servicenotification.models.Notification;
import org.laurichapp.servicenotification.repositories.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.laurichapp.servicenotification.utils.PageableUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FacadeNotificationImpl implements FacadeNotification {

    private NotificationRepository notificationRepository;

    public  FacadeNotificationImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void creerNotification(String emailClient, String pseudoClient, NotificationFonction notificationFonction){
        Notification notification = new Notification();
        notification.setEmailClient(emailClient);
        notification.setPseudoClient(pseudoClient);
        notification.setDateCreation(LocalDateTime.now());
        notification.setEtat(NotificationEtat.EN_ATTENTE);
        notification.setType(NotificationType.MAIL);
        notification.setFonction(notificationFonction);
        notificationRepository.insert(notification);
    }

    @Override
    public Paginate<NotificationOutDTO> getAllNotifications(PaginateRequestDTO paginateRequestDTO) {
        Pageable pageable = PageableUtils.convert(paginateRequestDTO);
        Page<Notification> paginated = notificationRepository.findAll(pageable);

        List<NotificationOutDTO> dtos =paginated.stream().map(Notification::toDto).toList();

        return new Paginate<>(dtos, new Pagination(Math.toIntExact(paginated.getTotalElements()),
                paginateRequestDTO.limit(), paginateRequestDTO.page()));
    }

    @Override
    public NotificationOutDTO getNotificationById(String idNotification) throws NotificationNotFoundException {
        Optional<Notification> c = notificationRepository.findById(idNotification);
        if(c.isEmpty())
            throw new NotificationNotFoundException();
        return Notification.toDto(c.get());
    }
}
