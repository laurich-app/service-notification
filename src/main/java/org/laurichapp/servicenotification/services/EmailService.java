package org.laurichapp.servicenotification.services;

import org.laurichapp.servicenotification.dtos.EmailDTO;

import java.util.Optional;

public interface EmailService {

    void envoyerEmail(EmailDTO emailDTO, Optional<String>  nomTemplate);
}
