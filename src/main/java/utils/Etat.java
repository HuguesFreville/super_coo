package utils;

public class Etat {

    public final String nom;
    public final float duree;

    public Etat(String nom, float duree) {
        this.nom = nom;
        this.duree = duree;
    }

    @Override
    public String toString() {
        return nom;
    }
}
