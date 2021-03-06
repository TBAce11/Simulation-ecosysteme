import java.util.HashSet;
import java.util.Set;

public class UsineHerbivore extends Usine{
    private double debrouillardise;
    private double voraciteMin;
    private double voraciteMax;
    private Set<String> aliments = new HashSet<String>();

    public UsineHerbivore(){
        super();
        traceInitialisation.put("debrouillardise", false);
        traceInitialisation.put("voraciteMin", false);
        traceInitialisation.put("voraciteMax", false);
        traceInitialisation.put("aliments", false);
    }

    public Herbivore creerHerbivore(){
        Herbivore nouvelHerbivore = new Herbivore(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie, this.efficaciteEnergie, this.resilience,
                this.fertilite, this.ageFertilite, this.energieEnfant, this.debrouillardise, this.voraciteMin, this.voraciteMax, this.aliments);
        if ((traceInitialisation.containsValue(false))){
            System.out.println(traceInitialisation);
            return null;
        }
        return nouvelHerbivore;
    }

    public void setDebrouillardise(double debrouillardise) {
        if ((debrouillardise >= 0) && (debrouillardise <= 1)) {
            this.debrouillardise = debrouillardise;
            traceInitialisation.replace("debrouillardise", true);
        }
    }

    public void setVoraciteMin(double voraciteMin) {
        if ((voraciteMin >= 0) && (voraciteMin <= 1)) {
            this.voraciteMin = voraciteMin;
            traceInitialisation.replace("voraciteMin", true);
        }
    }

    public void setVoraciteMax(double voraciteMax) {
        if ((voraciteMax >= 0) && (voraciteMax <= 1)) {
            this.voraciteMax = voraciteMax;
            traceInitialisation.replace("voraciteMax", true);
        }
    }

    public void addAliment(String aliment) {
        if (aliment != null) {
            this.aliments.add(aliment);
            traceInitialisation.replace("aliments", true);
        }
    }

    public void setAliments (Set<String> aliments){
        this.aliments = aliments;
        if (aliments.size() != 0){
            traceInitialisation.replace("aliments", true);
        }
    }
}
