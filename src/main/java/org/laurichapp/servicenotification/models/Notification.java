package org.laurichapp.servicenotification.models;

import org.bson.types.ObjectId;
import org.laurichapp.servicenotification.dtos.out.NotificationOutDTO;
import org.laurichapp.servicenotification.enums.NotificationEtat;
import org.laurichapp.servicenotification.enums.NotificationFonction;
import org.laurichapp.servicenotification.enums.NotificationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;

@Document(collection = "notifications")
public class Notification {

    @Id
    @Field("_id")
    private ObjectId idNotification;

    @Field("email")
    private String emailClient;

    @Field("pseudo")
    private String pseudoClient;

    @Field("date_creation")
    private LocalDateTime dateCreation;

    private NotificationEtat etat;

    private NotificationType type;

    private NotificationFonction fonction;

    public ObjectId getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(ObjectId idNotification) {
        this.idNotification = idNotification;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public NotificationEtat getEtat() {
        return etat;
    }

    public void setEtat(NotificationEtat etat) {
        this.etat = etat;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationFonction getFonction() {
        return fonction;
    }

    public void setFonction(NotificationFonction fonction) {
        this.fonction = fonction;
    }

    public String getPseudoClient() {
        return pseudoClient;
    }

    public void setPseudoClient(String pseudoClient) {
        this.pseudoClient = pseudoClient;
    }

    public static NotificationOutDTO toDto(Notification notification) {
        return new NotificationOutDTO(notification.getIdNotification().toString(),
                notification.getEmailClient(),
                notification.getPseudoClient(),
                notification.getDateCreation(),
                notification.getEtat(),
                notification.getType(),
                notification.getFonction());
    }

}
