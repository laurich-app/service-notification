package org.laurichapp.servicenotification.dtos;

import java.util.Date;
import java.util.List;

public record CommandeDTO(String _id, Date date_creation, Double total, List<ProduitDTO> produits, String numero) {
}
