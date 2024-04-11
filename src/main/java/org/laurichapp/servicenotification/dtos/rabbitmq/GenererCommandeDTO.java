package org.laurichapp.servicenotification.dtos.rabbitmq;

import java.io.Serializable;

public record GenererCommandeDTO(String email, CommandeDTO commande) implements Serializable {
}
