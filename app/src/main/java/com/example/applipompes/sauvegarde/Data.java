package com.example.applipompes.sauvegarde;

import java.io.Serializable;

public class Data implements Serializable {
    private int nbPompesFaitesTotal;

    private String difficulte;
    private int ratio;

    private int hour;
    private int minutes;
    private Boolean notification;

    public Data() {
        nbPompesFaitesTotal = 0;
        difficulte = "normal";
        ratio = 1;
        hour = 11;
        minutes = 00;
        notification = true;
    }

    public int getNbPompesFaitesTotal() {
        return nbPompesFaitesTotal;
    }

    public void setNbPompesFaitesTotal(int nbPompesFaitesTotal) {
        this.nbPompesFaitesTotal = nbPompesFaitesTotal;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }
}
