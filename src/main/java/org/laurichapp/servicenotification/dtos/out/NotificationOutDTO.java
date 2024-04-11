package org.laurichapp.servicenotification.dtos.out;

import org.laurichapp.servicenotification.enums.NotificationEtat;
import org.laurichapp.servicenotification.enums.NotificationFonction;
import org.laurichapp.servicenotification.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationOutDTO(String _id, String email, String pseudo, LocalDateTime date_creation, NotificationEtat etat, NotificationType type, NotificationFonction fonction) {
}
