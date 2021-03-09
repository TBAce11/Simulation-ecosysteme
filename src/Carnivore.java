/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe enfant Carnivore qui étend la classe parent Animal. */

import java.util.List;
import java.util.Set;

public class Carnivore extends Animal {

    // Constructeur d'un carnivore
    public Carnivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie,
            double resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum,
            double debrouillardise, Set<String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite,
                energieEnfant, tailleMaximum, debrouillardise, aliments); // Appel au constructeur d'animal
    }

    // Méthode qui dirige la reproduction d'un carnivore
    protected void confirmationReproduction(List<? extends Organisme> carnivores, int energieSupplementaire) {
        UsineCarnivore usineCarnivoreDuLac = new UsineCarnivore(); // Initialisation d'une usine de carnivores vierge
        if (this.age >= this.ageFertilite) { // Si l'herbivore est assez mature pour concevoir un enfant
            usineCarnivoreDuLac.setAll(this); // Mise à jour de l'usine

            /*
             * La reproduction retourne l'énergie supplémentaire tout en effectuant les
             * opérations nécessaires sur le carnivore jusqu'à tant que l'énergie
             * supplémentaire est nulle
             */
            while (energieSupplementaire > 0) {
                if (Math.random() <= this.fertilite) { // Tentative de reproduction accomplie

                    // Cas où l'enfant de la carnivore sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutCarnivore(carnivores, usineCarnivoreDuLac);
                        energieSupplementaire -= (int) (this.energieEnfant);

                        // Cas où la carnivore ne possède pas assez d'énergie supplémentaire pour les
                        // transférer à son enfant
                    } else if ((energieSupplementaire < (int) (this.energieEnfant))
                            && (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutCarnivore(carnivores, usineCarnivoreDuLac);
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
        } else { // Le carnivore n'a pas encore atteint l'age de maturité pour se reproduire
            this.recyclageEnergie(energieSupplementaire);
        }
        this.viellir();
    }

    // Méthode qui ajoute un carnivore à la liste de carnivores mise en argument
    private <Carnivore> void ajoutCarnivore(List<Carnivore> carnivores, UsineCarnivore usineDuLac) {
        // Herbivore bebe = (Herbivore) usineDuLac.creerHerbivore();
        try {
            carnivores.add((Carnivore) usineDuLac.creerCarnivore());
        } catch (ConditionsInitialesInvalides conditionsInitialesInvalides) {
            conditionsInitialesInvalides.printStackTrace();
        }
        /* System.out.println("bebe" + bebe); */
    }
}
