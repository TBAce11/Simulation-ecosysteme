/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe enfant Herbivore qui étend la classe parent Animal. */

import java.util.List;
import java.util.Set;

public class Herbivore extends Animal {
    private double voraciteMin;
    private double voraciteMax;

    // Constructeur d'un herbivore
    public Herbivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie,
            double resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum,
            double debrouillardise, double voraciteMin, double voraciteMax, Set<String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite,
                energieEnfant, tailleMaximum, debrouillardise, aliments); // Appel au constructueur d'un animal
        this.voraciteMin = voraciteMin;
        this.voraciteMax = voraciteMax;
    }

    // Getter de la valeur minimale de voracité
    public double getVoraciteMin() {
        return this.voraciteMin;
    }

    // Getter de la valeur maximale de voracité
    public double getVoraciteMax() {
        return this.voraciteMax;
    }

    // Méthode qui dirige la reproduction d'un herbivore
    protected void confirmationReproduction(List<? extends Organisme> herbivores, int energieSupplementaire) {
        UsineHerbivore usineHerbivoreDuLac = new UsineHerbivore(); // Initialisation d'une usine de herbivores vierge
        if (this.age >= this.ageFertilite) { // Si l'herbivore est assez mature pour concevoir un enfant
            usineHerbivoreDuLac.setAll(this); // Mise à jour de l'usine

            /*
             * La reproduction retourne l'énergie supplémentaire tout en effectuant les
             * opérations nécessaires sur l'herbivore jusqu'à tant que l'énergie
             * supplémentaire est nulle
             */
            while (energieSupplementaire > 0) {
                if (Math.random() <= this.fertilite) { // Tentative de reproduction accomplie

                    // Cas où l'enfant de l'herbivore sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutHerbivore(herbivores, usineHerbivoreDuLac);
                        energieSupplementaire -= (int) (this.energieEnfant);

                        // Cas où l'herbivore ne possède pas assez d'énergie supplémentaire pour les
                        // transférer à son enfant
                    } else if ((energieSupplementaire < (int) (this.energieEnfant))
                            && (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutHerbivore(herbivores, usineHerbivoreDuLac);
                        retraitEnergie(energieSupplementaire);
                        energieSupplementaire = 0;
                        /*
                         * Cas où l'énergie de l'adulte est insuffisante pour subvenir aux besoins de
                         * l'enfant même avec l'aide de l'énergie supplémentaire
                         */
                    } else {
                        this.recyclageEnergie(energieSupplementaire);
                        energieSupplementaire = 0;
                    }
                } else { // Tentative de reproduction échouée
                    energieSupplementaire--;
                }
            }
        } else { // L'herbivore n'a pas encore atteint l'age de maturité pour se reproduire
            this.recyclageEnergie(energieSupplementaire);
        }
        this.viellir();
    }

    // Méthode qui ajoute un herbivore à la liste de herbivores mise en argument
    private <Herbivore> void ajoutHerbivore(List<Herbivore> herbivores, UsineHerbivore usineDuLac) {
        try {
            herbivores.add((Herbivore) usineDuLac.creerHerbivore());
        } catch (ConditionsInitialesInvalides conditionsInitialesInvalides) {
            conditionsInitialesInvalides.printStackTrace();
        }
    }

}
