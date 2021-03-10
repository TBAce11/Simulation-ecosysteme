/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe enfant Plante qui étend la classe parent Organisme. */

import java.util.List;

public class Plante extends Organisme {

    // Constructeur d'une plante
    public Plante(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie,
            double resilience, double fertilite, int ageFertilite, double energieEnfant) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite,
                energieEnfant); // Constructeur d'un organisme
    }

    // Méthode qui dirige la reproduction d'une plante
    protected void confirmationReproduction(List<? extends Organisme> plantes, int energieSupplementaire) {
        UsinePlante usinePlanteDuLac = new UsinePlante(); // Initialisation d'une usine de plantes vierge

        if (this.age >= this.ageFertilite) { // Si la plante est assez mature pour concevoir un enfant
            usinePlanteDuLac.setAll(this); // Mise à jour de l'usine
            /*
             * La reproduction retourne l'énergie supplémentaire tout en effectuant les opérations nécessaires
             * sur la plante jusqu'à tant que l'énergie supplémentaire est nulle
             */
            while (energieSupplementaire > 0) {
                if (Math.random() <= this.fertilite) { // Tentative de reproduction accomplie

                    // Cas où l'enfant de la plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutPlante(plantes, usinePlanteDuLac);
                        energieSupplementaire -= (int) (this.energieEnfant);

                        // Cas où la plante ne possède pas assez d'énergie supplémentaire pour les transférer à son
                        // enfant
                    } else if ((energieSupplementaire < (int) (this.energieEnfant))
                            && (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutPlante(plantes, usinePlanteDuLac);
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
        } else { // La plante n'a pas encore atteint l'age de maturité pour se reproduire
            this.recyclageEnergie(energieSupplementaire);
        }
        this.viellir();
    }

    // Méthode qui ajoute une plante à la liste de plantes mise en argument
    private <Plante> void ajoutPlante(List<Plante> plantes, UsinePlante usineDuLac) {
        try {
            plantes.add((Plante) usineDuLac.creerPlante());
        } catch (ConditionsInitialesInvalides conditionsInitialesInvalides) {
            conditionsInitialesInvalides.printStackTrace();
        }
    }

    // Méthode qui retire l'énergie perdue d'une plante lorsqu'un animal la mange
    protected double transfertEnergie(double voracite) {
        double energiePerdue = this.energie * voracite;
        this.energie -= energiePerdue;
        return energiePerdue;
    }
}
