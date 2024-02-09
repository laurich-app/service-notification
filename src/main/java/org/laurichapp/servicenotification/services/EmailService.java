package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.models.Email;

import java.util.Optional;

public interface EmailService {

    void envoyerEmail(Email email, Optional<String>  nomTemplate, Optional<String> cheminPieceJointe);
}
