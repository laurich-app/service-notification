package org.laurichapp.servicenotification.dtos.rabbitmq;

import java.io.Serializable;

public record EmailDTO (String email, String pseudo) implements Serializable {
}