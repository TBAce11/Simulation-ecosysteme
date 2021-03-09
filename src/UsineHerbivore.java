import java.util.HashSet;
import java.util.Set;

public class UsineHerbivore extends Usine {
    private int tailleMaximum;
    private double debrouillardise;
    private double voraciteMin;
    private double voraciteMax;
    private Set<String> aliments = new HashSet<String>();

    public UsineHerbivore() {
        super();
        traceInitialisation.put("tailleMaximum", false);
        traceInitialisation.put("debrouillardise", false);
        traceInitialisation.put("voraciteMin", false);
        traceInitialisation.put("voraciteMax", false);
        traceInitialisation.put("aliments", false);
    }

    protected Herbivore creerHerbivore() throws ConditionsInitialesInvalides {
        Herbivore nouvelHerbivore = new Herbivore(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie,
                this.efficaciteEnergie, this.resilience, this.fertilite, this.ageFertilite, this.energieEnfant,
                this.tailleMaximum, this.debrouillardise, this.voraciteMin, this.voraciteMax, this.aliments);
        if ((traceInitialisation.containsValue(false))) {
            attributNonInitialise(traceInitialisation);
        }
        return nouvelHerbivore;
    }

    protected void setTailleMaximum(int tailleMaximum) {
        if (tailleMaximum > 0) {
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

    protected void setVoraciteMin(double voraciteMin) {
        if ((voraciteMin >= 0) && (voraciteMin <= 1)) {
            this.voraciteMin = voraciteMin;
            traceInitialisation.replace("voraciteMin", true);
        }
    }

    protected void setVoraciteMax(double voraciteMax) {
        if ((voraciteMax >= 0) && (voraciteMax <= 1)) {
            this.voraciteMax = voraciteMax;
            traceInitialisation.replace("voraciteMax", true);
        }
    }

    protected void addAliment(String aliment) {
        if (aliment != null) {
            this.aliments.add(aliment);
            traceInitialisation.replace("aliments", true);
        }
    }

    protected void setAliments(Set<String> aliments) {
        this.aliments = aliments;
        if (aliments.size() != 0) {
            traceInitialisation.replace("aliments", true);
        }
    }

    protected void evaluationPresenceTaille() {
        if ((!(traceInitialisation.get("tailleMaximum"))) && (traceInitialisation.get("energieEnfant"))) {
            this.setTailleMaximum((int) this.energieEnfant * 10);
        }
    }

    public void setAll(Herbivore herbivore) {
        setNomEspece(herbivore.getNomEspece());
        setBesoinEnergie(herbivore.getBesoinEnergie());
        setEfficaciteEnergie(herbivore.getEfficaciteEnergie());
        setResilience(herbivore.getResilience());
        setFertilite(herbivore.getFertilite());
        setAgeFertilite(herbivore.getAgeFertilite());
        setEnergieEnfant(herbivore.getEnergieEnfant());
        setVoraciteMin(herbivore.getVoraciteMin());
        setVoraciteMax(herbivore.getVoraciteMax());
        setTailleMaximum(herbivore.getTailleMaximum());
        setDebrouillardise(herbivore.getDebrouillardise());
        setAliments(herbivore.getAliments());
    }
}
