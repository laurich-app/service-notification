package org.laurichapp.servicenotification.dtos;

import java.time.LocalDateTime;

public record EmailDTO (String destinataire, String objet, String contenu, LocalDateTime date, String cheminPieceJointe, String pseudoDestinataire) {
}