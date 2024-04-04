package org.laurichapp.servicenotification.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.laurichapp.servicenotification.dtos.CommandeDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Commande {

    private String id;
    private Date dateCreation;
    private Double total;
    private List<Produit> produits;
    private String numero;

    public static Commande fromDTO(CommandeDTO commandeDTO){
        return new Commande(commandeDTO._id(),
                commandeDTO.date_creation(),
                commandeDTO.total(),
                commandeDTO.produits().stream().map(Produit::fromDTO).toList(),
                commandeDTO.numero());
    }
}
