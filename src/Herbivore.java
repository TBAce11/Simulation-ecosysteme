import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Herbivore extends Animal{
    private double voraciteMin;
    private double voraciteMax;

    public Herbivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum, double debrouillardise, double voraciteMin, double voraciteMax, Set <String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite, energieEnfant, tailleMaximum, debrouillardise, aliments);
        this.voraciteMin = voraciteMin;
        this.voraciteMax = voraciteMax;
    }


    protected void confirmationReproduction(List<? extends Organisme> herbivores, int energieSupplementaire) {
        UsineHerbivore usineHerbivoreDuLac = new UsineHerbivore();
        if (this.age >= this.ageFertilite) { //plante assez mature pour concevoir un enfant
            usineHerbivoreDuLac.setNomEspece(this.nomEspece);
            usineHerbivoreDuLac.setBesoinEnergie(this.besoinEnergie);
            usineHerbivoreDuLac.setEfficaciteEnergie(this.efficaciteEnergie);
            usineHerbivoreDuLac.setResilience(this.resilience);
            usineHerbivoreDuLac.setFertilite(this.fertilite);
            usineHerbivoreDuLac.setAgeFertilite(this.ageFertilite);
            usineHerbivoreDuLac.setEnergieEnfant(this.energieEnfant);
            usineHerbivoreDuLac.setTailleMaximum(this.tailleMaximum);
            usineHerbivoreDuLac.setDebrouillardise(this.debrouillardise);
            usineHerbivoreDuLac.setVoraciteMin(this.voraciteMin);
            usineHerbivoreDuLac.setVoraciteMax(this.voraciteMax);
            usineHerbivoreDuLac.setAliments(this.aliments);

            while (energieSupplementaire > 0) {
                //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                if (Math.random() <= this.fertilite) { //cas où l'enfant plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutHerbivore(herbivores, usineHerbivoreDuLac);
                        energieSupplementaire -= (int) (this.energieEnfant);

                    } else if ((energieSupplementaire < (int) (this.energieEnfant)) &&
                            (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutHerbivore(herbivores, usineHerbivoreDuLac);
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

    private <Herbivore> void ajoutHerbivore(List<Herbivore> herbivores, UsineHerbivore usineDuLac){
        try {
            herbivores.add((Herbivore) usineDuLac.creerHerbivore()); //ajout de l'enfant dans la liste de plantes
        } catch (ConditionsInitialesInvalides conditionsInitialesInvalides) {
            conditionsInitialesInvalides.printStackTrace();
        }
    }

    public double getVoraciteMin(){ return this.voraciteMin; }

    public double getVoraciteMax() { return this.voraciteMax; }

}
