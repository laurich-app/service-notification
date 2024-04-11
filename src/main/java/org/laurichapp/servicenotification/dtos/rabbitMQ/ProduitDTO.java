package org.laurichapp.servicenotification.dtos.rabbitMQ;

import java.io.Serializable;

public record ProduitDTO(double prix_unitaire, String sexe, String taille, String libelle, String description, CouleurDTO couleur, int quantite, CategorieDTO categorie) implements Serializable {
}
