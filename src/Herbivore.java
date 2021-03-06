import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Herbivore extends Organisme{
    private double debrouillardise;
    private double voraciteMin;
    private double voraciteMax;
    private Set<String> aliments = new HashSet<String>();


    public Herbivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant, double debrouillardise, double voraciteMin, double voraciteMax, Set <String> aliments) {
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite, energieEnfant);
        this.debrouillardise = debrouillardise;
        this.voraciteMin = voraciteMin;
        this.voraciteMax = voraciteMax;
        this.aliments = aliments;
    }

    public double getDebrouillardise(){ return this.debrouillardise; }

    public double getVoraciteMin(){ return this.voraciteMin; }

    public double getVoraciteMax(){ return this.voraciteMax; }

    public Set<String> getAliments(){ return this.aliments;}

    public void confirmationReproduction(Usine usine, List<? extends Organisme> herbivores, int energieSupplementaire) {
        UsineHerbivore usineHerbivoreDuLac = (UsineHerbivore) usine;
        if (this.age >= this.ageFertilite) { //plante assez mature pour concevoir un enfant
            usineHerbivoreDuLac.setNomEspece(this.nomEspece);
            usineHerbivoreDuLac.setBesoinEnergie(this.besoinEnergie);
            usineHerbivoreDuLac.setEfficaciteEnergie(this.efficaciteEnergie);
            usineHerbivoreDuLac.setResilience(this.resilience);
            usineHerbivoreDuLac.setFertilite(this.fertilite);
            usineHerbivoreDuLac.setAgeFertilite(this.ageFertilite);
            usineHerbivoreDuLac.setEnergieEnfant(this.energieEnfant);
            usineHerbivoreDuLac.setDebrouillardise(this.debrouillardise);
            usineHerbivoreDuLac.setVoraciteMin(this.voraciteMin);
            usineHerbivoreDuLac.setVoraciteMax(this.voraciteMax);
            usineHerbivoreDuLac.setAliments(this.aliments);

            System.out.println("Herbivore mature: " + this.age + " ans");
            while (energieSupplementaire > 0) {
                //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                if (Math.random() <= this.fertilite) { //cas où l'enfant plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        ajoutHerbivore(herbivores, usineHerbivoreDuLac);
                        System.out.println(herbivores.get(herbivores.size()-1));
                        System.out.println("1 enfant créé");
                        System.out.println("Supp avant: " + energieSupplementaire);
                        energieSupplementaire -= (int) (this.energieEnfant);
                        System.out.println("Supp après: " + energieSupplementaire);

                    } else if ((energieSupplementaire < (int) (this.energieEnfant)) &&
                            (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        ajoutHerbivore(herbivores, usineHerbivoreDuLac);
                        System.out.println(herbivores.get(herbivores.size()-1));
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
            System.out.println("Herbivore immature.");
            this.recyclageEnergie(energieSupplementaire);
        }
        this.viellir();
    }

    private <Herbivore> void ajoutHerbivore(List<Herbivore> herbivores, UsineHerbivore usineDuLac) {
        //Herbivore bebe = (Herbivore) usineDuLac.creerHerbivore();
        herbivores.add((Herbivore) usineDuLac.creerHerbivore()); //ajout de l'enfant dans la liste de plantes
        /*System.out.println("bebe" + bebe);*/
    }

    protected void recyclageEnergie(int energieSupplementaire){ //-> voir si elle peut être implémentée dans Organisme
        System.out.println("Energie avant: " + this.energie);
        this.energie += energieSupplementaire * this.efficaciteEnergie;
        System.out.println("Energie après: " + this.energie);
    }

    protected void retraitEnergie(double energieSupplementaire){
        System.out.println("Energie avant: " + this.energie);
        this.energie -= this.energieEnfant - energieSupplementaire;
        System.out.println("Energie après: " + this.energie);
    }
}
