import java.util.HashSet;
import java.util.Set;

public class UsineCarnivore extends Usine {
    private int tailleMaximum;
    private double debrouillardise;
    private Set<String> aliments = new HashSet<String>();

    public UsineCarnivore(){
        super();
        traceInitialisation.put("tailleMaximum", false);
        traceInitialisation.put("debrouillardise", false);
        traceInitialisation.put("aliments", false);
    }

    protected Carnivore creerCarnivore(){
        Carnivore nouvelCarnivore = new Carnivore(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie, this.efficaciteEnergie, this.resilience,
                this.fertilite, this.ageFertilite, this.energieEnfant, this.tailleMaximum, this.debrouillardise, this.aliments);
        if ((traceInitialisation.containsValue(false))){
            System.out.println(traceInitialisation);
            return null;
        }
        return nouvelCarnivore;
    }

    protected void setTailleMaximum(int tailleMaximum){
        if (tailleMaximum > 0){
            this.tailleMaximum = tailleMaximum;
            traceInitialisation.replace("tailleMaximum", true);
        }
    }

    protected void setDebrouillardise(double debrouillardise) {
        if ((debrouillardise >= 0) && (debrouillardise <= 1)) {
            this.debrouillardise = debrouillardise;
            traceInitialisation.replace("debrouillardise", true);
        }
    }

    protected void addAliment(String aliment) {
        if (aliment != null) {
            this.aliments.add(aliment);
            traceInitialisation.replace("aliments", true);
        }
    }

    protected void setAliments (Set<String> aliments){
        this.aliments = aliments;
        if (aliments.size() != 0){
            traceInitialisation.replace("aliments", true);
        }
    }
    protected void evaluationPresenceTaille(){
        if ((!(traceInitialisation.get("tailleMaximum"))) && (traceInitialisation.get("energieEnfant"))){
            setTailleMaximum((int) this.energieEnfant * 10);
        }
    }
}
