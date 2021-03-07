import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Carnivore extends Organisme {
    private int tailleMaximum;
    private double debrouillardise;
    private Set<String> aliments = new HashSet<String>();

    public Carnivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum, double debrouillardise, Set <String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite, energieEnfant);
        this.tailleMaximum = tailleMaximum;
        this.debrouillardise = debrouillardise;
        this.aliments = aliments;
    }

    protected double getDebrouillardise() {
        return this.debrouillardise;
    }

    protected Set<String> getAliments() {
        return this.aliments;
    }

    protected int getTailleMaximum(){
        return this.tailleMaximum;
    }

    protected void confirmationReproduction(Usine usine, List<? extends Organisme> carnivores, int energieSupplementaire) {
        UsineCarnivore usineCarnivoreDuLac = (UsineCarnivore) usine;
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

            System.out.println("Carnivore mature: " + this.age + " ans");
            while (energieSupplementaire > 0) {
                //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                if (Math.random() <= this.fertilite) { //cas où l'enfant plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutCarnivore(carnivores, usineCarnivoreDuLac);
                        System.out.println("1 enfant créé");
                        System.out.println("Supp avant: " + energieSupplementaire);
                        energieSupplementaire -= (int) (this.energieEnfant);
                        System.out.println("Supp après: " + energieSupplementaire);

                    } else if ((energieSupplementaire < (int) (this.energieEnfant)) &&
                            (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutCarnivore(carnivores, usineCarnivoreDuLac);
                        System.out.println("1 enfant créé");
                        retraitEnergie(energieSupplementaire);
                        System.out.println("Supp avant: " + energieSupplementaire);
                        energieSupplementaire = 0;

                    } else { //l'énergie de l'adulte est insuffisante pour subvenir aux besoins de l'enfant même avec
                        //l'aide de l'énergie supplémentaire
                        System.out.println("Pas assez d'énergie pour concevoir un nouvel enfant!");
                        this.recyclageEnergie(energieSupplementaire);
                        energieSupplementaire = 0;
                    }
                } else { //tentative de reproduction échouée
                    System.out.println("Reproduction échouée -> supp: " + energieSupplementaire);
                    energieSupplementaire--;
                }
            }
        } else {
            System.out.println("Carnivore immature.");
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
