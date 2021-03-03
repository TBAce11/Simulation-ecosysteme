import java.util.List;
import java.util.Set;

public class Herbivore extends Organisme{
    private String nomEspece;
    private double energie;
    private int age;
    private double besoinEnergie;
    private double efficaciteEnergie;
    private double resilience;
    private double fertilite;
    private int ageFertilite;
    private double energieEnfant;

    private double debrouillardise;
    private double voraciteMin;
    private double voraciteMax;
    private Set<String> aliments;


    public Herbivore(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant, double debrouillardise, double voraciteMin, double voraciteMax, Set <String> aliments) {
        this.nomEspece = nomEspece;
        this.energie = energie;
        this.age = age;
        this.besoinEnergie = besoinEnergie;
        this.efficaciteEnergie = efficaciteEnergie;
        this.resilience = resilience;
        this.fertilite = fertilite;
        this.ageFertilite = ageFertilite;
        this.energieEnfant = energieEnfant;

        this.debrouillardise = debrouillardise;
        this.voraciteMin = voraciteMin;
        this.voraciteMax = voraciteMax;
        this.aliments = aliments;
    }

    @Override
    public String getNomEspece() {
        return this.nomEspece;
    }

    @Override
    public double getEnergie() {
        return this.energie;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public double getBesoinEnergie() {
        return this.besoinEnergie;
    }

    @Override
    public double getEfficaciteEnergie() {
        return this.efficaciteEnergie;
    }

    @Override
    public double getResilience() {
        return this.resilience;
    }

    @Override
    public double getFertilite() {
        return this.fertilite;
    }

    @Override
    public int getAgeFertilite() {
        return this.ageFertilite;
    }

    @Override
    public double getEnergieEnfant() {
        return this.energieEnfant;
    }

    public double getDebrouillardise(){ return this.debrouillardise; }

    public double getVoraciteMin(){ return this.voraciteMin; }

    public double getVoraciteMax(){ return this.voraciteMax; }

    public Set<String> getAliments(){ return this.aliments;}

    @Override
    public boolean survie(double energieAbsorbee){
    }

    @Override
    public void confirmationReproduction(UsinePlante usineDuLac, List<Plante> plantes, int energieSupplementaire){

    }

    @Override
    public void recyclageEnergie(int energieSupplementaire){

    }

    @Override
    public void retraitEnergie(double energieSupplementaire){

    }
}
