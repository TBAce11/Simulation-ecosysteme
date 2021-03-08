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

    final protected String getNomEspece() {
        return this.nomEspece;
    }

    final protected double getEnergie() {
        return this.energie;
    }

    final protected int getAge() {
        return this.age;
    }

    final protected double getBesoinEnergie() {
        return this.besoinEnergie;
    }

    final protected double getEfficaciteEnergie() {
        return this.efficaciteEnergie;
    }

    final protected double getResilience() {
        return this.resilience;
    }

    final protected double getFertilite() {
        return this.fertilite;
    }

    final protected int getAgeFertilite() {
        return this.ageFertilite;
    }

    final protected double getEnergieEnfant() {
        return this.energieEnfant;
    }

    final protected void viellir(){
        this.age++;
    }

    protected boolean survie(double energieAbsorbee){
        int uniteEnergieManquante = (int) (this.besoinEnergie - energieAbsorbee);
        double chanceSurvie = Math.pow(this.resilience, uniteEnergieManquante);

        if (Math.random() <= chanceSurvie){
            this.energie -= (this.besoinEnergie - energieAbsorbee);
            this.viellir();
            if (Math.round(this.energie) <= 0){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    protected abstract void confirmationReproduction(List<? extends Organisme> liste, int energieSupplementaire);

    final protected void recyclageEnergie(int energieSupplementaire){ //-> voir si elle peut être implémentée dans Organisme
        this.energie += energieSupplementaire * this.efficaciteEnergie;
    }

    final protected void retraitEnergie(double energieSupplementaire){
        this.energie -= this.energieEnfant - energieSupplementaire;
    }
}
