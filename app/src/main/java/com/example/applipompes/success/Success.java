package com.example.applipompes.success;

public class Success {

    private String nom;
    private int color;
    private int nbPompesRequis;

    public Success(String nom, int couleur, int nbPompesRequis) {
        this.nom = nom;
        this.color = couleur;
        this.nbPompesRequis = nbPompesRequis;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNbPompesRequis() {
        return nbPompesRequis;
    }

    public void setNbPompesRequis(int nbPompesRequis) {
        this.nbPompesRequis = nbPompesRequis;
    }
}
