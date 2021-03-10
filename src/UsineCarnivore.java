/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe enfant UsineCarnivore qui étend la classe parent Usine. */

import java.util.HashSet;
import java.util.Set;

public class UsineCarnivore extends Usine {
    private int tailleMaximum;
    private double debrouillardise;
    private Set<String> aliments = new HashSet<String>();

    // Constructeur d'une usine de carnivores
    public UsineCarnivore() {
        super(); // Appel au constructeur d'une usine
        traceInitialisation.put("tailleMaximum", false);
        traceInitialisation.put("debrouillardise", false);
        traceInitialisation.put("aliments", false);
    }

    // Méthode qui appelle le constructeur d'un carnivore et qui lui transmet ses attributs
    protected Carnivore creerCarnivore() throws ConditionsInitialesInvalides {

        // Évalue la présence d'attributs non-initialisés dans la trace de l'usine et envoie une exception si nécessaire
        if ((traceInitialisation.containsValue(false))) {
            attributNonInitialise(traceInitialisation);
        }
        Carnivore nouvelCarnivore = new Carnivore(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie,
                this.efficaciteEnergie, this.resilience, this.fertilite, this.ageFertilite, this.energieEnfant,
                this.tailleMaximum, this.debrouillardise, this.aliments);
        return nouvelCarnivore;
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

    /* Méthode qui calcule la taille maximum d'un carnivore à partir de son énergieEnfant si la taille n'est pas
       spécifié dans le fichier XML */
    protected void evaluationPresenceTaille() {
        if ((!(traceInitialisation.get("tailleMaximum"))) && (traceInitialisation.get("energieEnfant"))) {
            setTailleMaximum((int) this.energieEnfant * 10);
        }
    }

    // Méthode qui initialise tous les attributs de l'usine à partir d'un carnivore mis en argument
    protected void setAll(Carnivore carnivore) {
        setNomEspece(carnivore.getNomEspece());
        setBesoinEnergie(carnivore.getBesoinEnergie());
        setEfficaciteEnergie(carnivore.getEfficaciteEnergie());
        setResilience(carnivore.getResilience());
        setFertilite(carnivore.getFertilite());
        setAgeFertilite(carnivore.getAgeFertilite());
        setEnergieEnfant(carnivore.getEnergieEnfant());
        setTailleMaximum(carnivore.getTailleMaximum());
        setDebrouillardise(carnivore.getDebrouillardise());
        setAliments(carnivore.getAliments());
    }
}
