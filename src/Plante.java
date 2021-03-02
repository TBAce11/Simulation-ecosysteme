import java.util.List;

public class Plante {
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

    public String getNomEspece() {
        return this.nomEspece;
    }

    public double getEnergie() {
        return this.energie;
    }

    public int getAge() {
        return this.age;
    }

    public double getBesoinEnergie() {
        return this.besoinEnergie;
    }

    public double getEfficaciteEnergie() {
        return this.efficaciteEnergie;
    }

    public double getResilience() {
        return this.resilience;
    }

    public double getFertilite() {
        return this.fertilite;
    }

    public int getAgeFertilite() {
        return this.ageFertilite;
    }

    public double getEnergieEnfant() {
        return this.energieEnfant;
    }

    public boolean survie(double energieAbsorbee) {
        int uniteEnergieManquante = (int) (energieAbsorbee - this.besoinEnergie);
        double chanceSurvie = Math.pow(this.resilience, uniteEnergieManquante);
        if (Math.random() <= chanceSurvie) {
            this.energie -= (this.besoinEnergie - energieAbsorbee);
            return true;
        } else {
            return false;
        }
    }

    public double reproduction(UsinePlante usineDuLac, List<Plante> plantes, int i, double energieSupplementaire) {
        if (Math.random() <= plantes.get(i).getFertilite()) { //cas où l'enfant plante sera créé
            if (energieSupplementaire >= plantes.get(i).getEnergieEnfant()) { //faut-il seulement faire affaire aux variables this. puisque reproduction est appelée à partir de la plante concernée?
                plantes.add(usineDuLac.creerPlante());
                energieSupplementaire -= plantes.get(i).getEnergieEnfant();
            } else {
                plantes.add(usineDuLac.creerPlante());
                plantes.get(i).retraitEnergie(energieSupplementaire);
                energieSupplementaire = 0;
            }
        } else { //tentative de reproduction échouée
            energieSupplementaire--;
        }
        return energieSupplementaire; //vérifier si l'énergie supplémentaire est bien changée
    }

    public void retraitEnergie(double energieSupplementaire){
        this.energie -= (this.energieEnfant - energieSupplementaire);
    }
}

