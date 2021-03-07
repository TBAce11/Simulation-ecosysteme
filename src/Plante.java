import java.util.List;

public class Plante extends Organisme {

    public Plante(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite, energieEnfant);
    }

    protected void confirmationReproduction(Usine usine, List<? extends Organisme> plantes, int energieSupplementaire) {
        UsinePlante usinePlanteDuLac = (UsinePlante) usine;
        if (this.age >= this.ageFertilite) { //plante assez mature pour concevoir un enfant
            usinePlanteDuLac.setNomEspece(this.nomEspece);
            usinePlanteDuLac.setBesoinEnergie(this.besoinEnergie);
            usinePlanteDuLac.setEfficaciteEnergie(this.efficaciteEnergie);
            usinePlanteDuLac.setResilience(this.resilience);
            usinePlanteDuLac.setFertilite(this.fertilite);
            usinePlanteDuLac.setAgeFertilite(this.ageFertilite);
            usinePlanteDuLac.setEnergieEnfant(this.energieEnfant);
            while (energieSupplementaire > 0) {
                //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                if (Math.random() <= this.fertilite) { //cas où l'enfant plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutPlante(plantes, usinePlanteDuLac);
                        energieSupplementaire -= (int) (this.energieEnfant);

                    } else if ((energieSupplementaire < (int) (this.energieEnfant)) &&
                            (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutPlante(plantes, usinePlanteDuLac);
                        retraitEnergie(energieSupplementaire);
                        energieSupplementaire = 0;

                    } else { //l'énergie de l'adulte est insuffisante pour subvenir aux besoins de l'enfant même avec
                        //l'aide de l'énergie supplémentaire
                        this.recyclageEnergie(energieSupplementaire);
                        energieSupplementaire = 0;
                    }
                } else { //tentative de reproduction échouée
                    energieSupplementaire--;
                }
            }
        } else {
            this.recyclageEnergie(energieSupplementaire);
        }
        this.viellir();
    }

    private <Plante> void ajoutPlante(List<Plante> plantes, UsinePlante usineDuLac) {
        plantes.add((Plante) usineDuLac.creerPlante()); //ajout de l'enfant dans la liste de plantes
    }

    protected double transfertEnergie(double voracite){
        double energiePerdue = this.energie * voracite;
        this.energie -= energiePerdue;
        return energiePerdue;
    }
}

