/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe parent Organisme qui s'étend aux classes enfants Animal et Plante. */

import java.lang.reflect.Array;
import java.util.List;

// Initialisation des variables locales
public abstract class Organisme {
    protected String nomEspece;
    protected double energie;
    protected int age;
    protected double besoinEnergie;
    protected double efficaciteEnergie;
    protected double resilience;
    protected double fertilite;
    protected int ageFertilite;
    protected double energieEnfant;

    // Constructeur d'un organisme
    public Organisme(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie,
            double resilience, double fertilite, int ageFertilite, double energieEnfant) {
        this.nomEspece = nomEspece;
        this.energie = energie;
        this.age = age;
        this.besoinEnergie = besoinEnergie;
        this.efficaciteEnergie = efficaciteEnergie;
        this.resilience = resilience;
        this.fertilite = fertilite;
        this.ageFertilite = ageFertilite;
        this.energieEnfant = energieEnfant;
    }

    final protected String getNomEspece() {
        return this.nomEspece;
    }

    final protected double getEnergie() {
        return this.energie;
    }

    final protected int getAge() {
        return this.age;
    }

    final protected double getBesoinEnergie() {
        return this.besoinEnergie;
    }

    final protected double getEfficaciteEnergie() {
        return this.efficaciteEnergie;
    }

    final protected double getResilience() {
        return this.resilience;
    }

    final protected double getFertilite() {
        return this.fertilite;
    }

    final protected int getAgeFertilite() {
        return this.ageFertilite;
    }

    final protected double getEnergieEnfant() {
        return this.energieEnfant;
    }

    // Méthode qui fait vieillir un organisme d'un an
    final protected void viellir() {
        this.age++;
    }

    // Méthode qui détermine si un organisme survie (True) ou non (False) en retournant un booléen
    protected boolean survie(double energieAbsorbee) {
        int uniteEnergieManquante = (int) (this.besoinEnergie - energieAbsorbee);
        double chanceSurvie = Math.pow(this.resilience, uniteEnergieManquante);

        if (Math.random() <= chanceSurvie) {
            this.energie -= (this.besoinEnergie - energieAbsorbee);
            this.viellir();
            if (Math.round(this.energie) <= 0) { // On impose la mort de l'organisme si son énergie arrondi est de 0
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // Méthode qui dirige la reproduction d'un organisme à partir de son énergie supplémentaire et qui ajoute
    // son enfant
    protected abstract void confirmationReproduction(List<? extends Organisme> liste, int energieSupplementaire);

    // Méthode qui recycle l'énergie supplémentaire d'un organisme à partir de son efficacité énergétique
    final protected void recyclageEnergie(int energieSupplementaire) {
        this.energie += energieSupplementaire * this.efficaciteEnergie;
    }

    // Méthode qui retire l'énergie nécessaire d'un organisme pour subvenir au besoin énergétique de son enfant
    // à l'aide de son énergie supplémentaire
    final protected void retraitEnergie(double energieSupplementaire) {
        this.energie -= this.energieEnfant - energieSupplementaire;
    }
}
