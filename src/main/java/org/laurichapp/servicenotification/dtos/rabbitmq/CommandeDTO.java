package org.laurichapp.servicenotification.dtos.rabbitmq;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public record CommandeDTO(String _id, Date date_creation, String id_utillisateur, String id_paiement, Double total, List<ProduitDTO> produits, String etat_livraison, String statut_paiement, String numero) implements Serializable {
}
