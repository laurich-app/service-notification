package org.laurichapp.servicenotification.dtos;

import java.io.Serializable;

public record EmailDTO (String email, String pseudo) implements Serializable {
}