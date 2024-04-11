package org.laurichapp.servicenotification.dtos.rabbitMQ;

import java.io.Serializable;

public record GenererCommandeDTO(String email, CommandeDTO commande) implements Serializable {
}
