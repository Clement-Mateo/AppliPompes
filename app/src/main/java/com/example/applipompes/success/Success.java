package com.example.applipompes.success;

import android.graphics.Color;

import java.util.ArrayList;

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

    public static ArrayList<Success> getSuccess() {
        ArrayList<Success> listSuccess = new ArrayList<>();

        listSuccess.add(new Success("NOUVEAU", Color.parseColor("#DB0000"), 1));
        listSuccess.add(new Success("APPRENTI", Color.parseColor("#DB0000"), 10));
        listSuccess.add(new Success("DEBUTANT", Color.parseColor("#DB0000"), 50));
        listSuccess.add(new Success("NOVICE", Color.parseColor("#DB0000"), 100));
        listSuccess.add(new Success("INITIÉ", Color.parseColor("#DB0000"), 1000));
        listSuccess.add(new Success("HABITUÉ", Color.parseColor("#DB0000"), 5000));
        listSuccess.add(new Success("PROFESSIONNEL", Color.parseColor("#DB0000"), 10000));
        listSuccess.add(new Success("EXPERT", Color.parseColor("#DB0000"), 30000));
        listSuccess.add(new Success("MAITRE", Color.parseColor("#DB0000"), 50000));
        listSuccess.add(new Success("CHAMPION", Color.parseColor("#DB0000"), 100000));
        listSuccess.add(new Success("LÉGENDE", Color.parseColor("#DB0000"), 200000));

        return listSuccess;
    }
}
