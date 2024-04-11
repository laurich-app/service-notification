package org.laurichapp.servicenotification.exceptions;

public class NotificationNotFoundException extends Exception {
    public NotificationNotFoundException() {
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
