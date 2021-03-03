import java.util.List;

public class Plante extends Organisme {
    private String nomEspece;
    private double energie;
    private int age;
    private double besoinEnergie;
    private double efficaciteEnergie;
    private double resilience;
    private double fertilite;
    private int ageFertilite;
    private double energieEnfant;

    public Plante(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant) {
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

    @Override
    public boolean survie(double energieAbsorbee) {
        int uniteEnergieManquante = (int) (this.besoinEnergie - energieAbsorbee);
        double chanceSurvie = Math.pow(this.resilience, uniteEnergieManquante);

        if (Math.random() <= chanceSurvie) {
            this.energie -= (this.besoinEnergie - energieAbsorbee);
            this.viellir();
            System.out.println("Plante survécue");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void confirmationReproduction(UsinePlante usineDuLac, List<Plante> plantes, int energieSupplementaire) {
        if (this.age >= this.ageFertilite) { //plante assez mature pour concevoir un enfant
            usineDuLac.setNomEspece(this.nomEspece);
            usineDuLac.setBesoinEnergie(this.besoinEnergie);
            usineDuLac.setEfficaciteEnergie(this.efficaciteEnergie);
            usineDuLac.setResilience(this.resilience);
            usineDuLac.setFertilite(this.fertilite);
            usineDuLac.setAgeFertilite(this.ageFertilite);
            usineDuLac.setEnergieEnfant(this.energieEnfant);
            System.out.println("Plante mature: " + this.age + " ans");
            while (energieSupplementaire > 0) {
                //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                if (Math.random() <= this.fertilite) { //cas où l'enfant plante sera créé
                    if (energieSupplementaire >= (int) (this.energieEnfant)) {
                        plantes.add(usineDuLac.creerPlante()); //ajout de l'enfant dans la liste de plantes
                        System.out.println("1 enfant créé");
                        System.out.println("Supp avant: " + energieSupplementaire);
                        energieSupplementaire -= (int) (this.energieEnfant);
                        System.out.println("Supp après: " + energieSupplementaire);

                    } else if ((energieSupplementaire < (int) (this.energieEnfant)) && (this.energie >= (this.energieEnfant - energieSupplementaire))) {
                        plantes.add(usineDuLac.creerPlante()); //ajout de l'enfant dans la liste de plantes
                        System.out.println("1 enfant créé");
                        retraitEnergie(energieSupplementaire);
                        System.out.println("Supp avant: " + energieSupplementaire);
                        energieSupplementaire = 0;

                    } else { //l'énergie de l'adulte est insuffisante pour subvenir aux besoins de l'enfant
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
            System.out.println("Plante immature.");
            this.recyclageEnergie(energieSupplementaire);
        }
        this.viellir();
    }

    @Override
    public void recyclageEnergie(int energieSupplementaire) {
        System.out.println("Energie avant: " + this.energie);
        this.energie += energieSupplementaire * this.efficaciteEnergie;
        System.out.println("Energie après: " + this.energie);
    }

    @Override
    public void retraitEnergie(double energieSupplementaire){
        System.out.println("Energie avant: " + this.energie);
        this.energie -= this.energieEnfant - energieSupplementaire;
        System.out.println("Energie après: " + this.energie);
    }
}

