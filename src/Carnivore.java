import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Carnivore extends Animal {

    public Carnivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum, double debrouillardise, Set <String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite, energieEnfant, tailleMaximum,
        debrouillardise, aliments);
    }

    protected void confirmationReproduction(List<? extends Organisme> carnivores, int energieSupplementaire) {
        UsineCarnivore usineCarnivoreDuLac = new UsineCarnivore();
        if (this.age >= this.ageFertilite) { //plante assez mature pour concevoir un enfant
            usineCarnivoreDuLac.setNomEspece(this.nomEspece);
            usineCarnivoreDuLac.setBesoinEnergie(this.besoinEnergie);
            usineCarnivoreDuLac.setEfficaciteEnergie(this.efficaciteEnergie);
            usineCarnivoreDuLac.setResilience(this.resilience);
            usineCarnivoreDuLac.setFertilite(this.fertilite);
            usineCarnivoreDuLac.setAgeFertilite(this.ageFertilite);
            usineCarnivoreDuLac.setEnergieEnfant(this.energieEnfant);
            usineCarnivoreDuLac.setTailleMaximum(this.tailleMaximum);
            usineCarnivoreDuLac.setDebrouillardise(this.debrouillardise);
            usineCarnivoreDuLac.setAliments(this.aliments);

            while (energieSupplementaire > 0) {
                //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                if (Math.random() <= this.fertilite) { //cas où l'enfant plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutCarnivore(carnivores, usineCarnivoreDuLac);
                        energieSupplementaire -= (int) (this.energieEnfant);

                    } else if ((energieSupplementaire < (int) (this.energieEnfant)) &&
                            (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutCarnivore(carnivores, usineCarnivoreDuLac);
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

    private <Carnivore> void ajoutCarnivore(List<Carnivore> carnivores, UsineCarnivore usineDuLac) {
        //Herbivore bebe = (Herbivore) usineDuLac.creerHerbivore();
        carnivores.add((Carnivore) usineDuLac.creerCarnivore()); //ajout de l'enfant dans la liste de plantes
        /*System.out.println("bebe" + bebe);*/
    }
}
