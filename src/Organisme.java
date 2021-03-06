import java.lang.reflect.Array;
import java.util.List;

public abstract class Organisme {
    protected String nomEspece;
    protected double energie;
    protected int age;
    protected double besoinEnergie;
    protected double efficaciteEnergie;
    protected double resilience;
    protected double fertilite;
    protected int ageFertilite;
    protected double energieEnfant;

    public Organisme(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant){
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

    final public String getNomEspece() {
        return this.nomEspece;
    }

    final public double getEnergie() {
        return this.energie;
    }

    final public int getAge() {
        return this.age;
    }

    final public double getBesoinEnergie() {
        return this.besoinEnergie;
    }

    final public double getEfficaciteEnergie() {
        return this.efficaciteEnergie;
    }

    final public double getResilience() {
        return this.resilience;
    }

    final public double getFertilite() {
        return this.fertilite;
    }

    final public int getAgeFertilite() {
        return this.ageFertilite;
    }

    final public double getEnergieEnfant() {
        return this.energieEnfant;
    }

    final public void viellir(){
        this.age++;
    }

    public boolean survie(double energieAbsorbee){
        int uniteEnergieManquante = (int) (this.besoinEnergie - energieAbsorbee);
        double chanceSurvie = Math.pow(this.resilience, uniteEnergieManquante);

        if (Math.random() <= chanceSurvie){
            this.energie -= (this.besoinEnergie - energieAbsorbee);
            this.viellir();

            if (this.energie <= 0){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public abstract void confirmationReproduction(Usine usineDuLac, List<? extends Organisme> liste, int energieSupplementaire);

    protected abstract void recyclageEnergie(int energieSupplementaire);

    protected abstract void retraitEnergie(double energieSupplementaire);
}
