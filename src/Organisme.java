import java.util.List;

public abstract class Organisme {
    private String nomEspece;
    private double energie;
    private int age;
    private double besoinEnergie;
    private double efficaciteEnergie;
    private double resilience;
    private double fertilite;
    private int ageFertilite;
    private double energieEnfant;

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

    public void viellir(){
        this.age++;
    }

    public abstract boolean survie(double energieAbsorbee);

    public abstract void confirmationReproduction(UsinePlante usineDuLac, List<Plante> plantes, int energieSupplementaire);

    public abstract void recyclageEnergie(int energieSupplementaire);

    public abstract void retraitEnergie(double energieSupplementaire);
}
