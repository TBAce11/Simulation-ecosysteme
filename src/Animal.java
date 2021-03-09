/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe parent Animal qui sera étendu à ses classes enfants Herbivore et Carnivore.Il est 
aussi une classe enfant de la classe Organisme. */

import java.util.HashSet;
import java.util.Set;

public abstract class Animal extends Organisme {

    // Initialisation des variables locales
    protected int tailleMaximum;
    protected double debrouillardise;
    protected Set<String> aliments = new HashSet<String>();

    // Constructeur d'un animal à partir d'un organisme
    public Animal(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie,
            double resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum,
            double debrouillardise, Set<String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite,
                energieEnfant); // Appel au constructeur d'un organisme
        this.tailleMaximum = tailleMaximum;
        this.debrouillardise = debrouillardise;
        this.aliments = aliments;
    }

    // Getter de la valeur de debrouillardise
    protected double getDebrouillardise() {
        return this.debrouillardise;
    }

    // Getter de la liste d'aliments
    protected Set<String> getAliments() {
        return this.aliments;
    }

    // Getter de la valeur de la taille maximum
    protected int getTailleMaximum() {
        return this.tailleMaximum;
    }

    // Getter de la valeur debrouillardise
    protected void miseAJourTaille() {
        if (this.tailleMaximum < this.energieEnfant * 10) {
            this.tailleMaximum = (int) this.energieEnfant * 10;
        }
    }

}
