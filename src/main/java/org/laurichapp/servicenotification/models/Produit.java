package org.laurichapp.servicenotification.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.laurichapp.servicenotification.dtos.ProduitDTO;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Produit {

    private String couleur;
    private int quantite;
    private String libelle;
    private double prix_unitaire;
    private String taille;

    public static Produit fromDTO(ProduitDTO produitDTO){
        return new Produit(produitDTO.couleur().libelle(),
                produitDTO.quantite(),
                produitDTO.libelle(),
                produitDTO.prix_unitaire(),
                produitDTO.taille());
    }
}
