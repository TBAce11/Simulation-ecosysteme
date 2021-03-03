import java.util.HashMap;

public class UsinePlante {
    private String nomEspece;
    private double besoinEnergie;
    private double efficaciteEnergie;
    private double resilience;
    private double fertilite;
    private int ageFertilite;
    private double energieEnfant;
    HashMap<String, Boolean> traceInitialisation = new HashMap<String, Boolean>();

    public UsinePlante() {
        traceInitialisation.put("nomEspece", false);
        traceInitialisation.put("besoinEnergie", false);
        traceInitialisation.put("efficaciteEnergie", false);
        traceInitialisation.put("resilience", false);
        traceInitialisation.put("fertilite", false);
        traceInitialisation.put("ageFertilite", false);
        traceInitialisation.put("energieEnfant", false);
    }

    public Plante creerPlante() {
        Plante nouvellePlante = new Plante(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie,
                this.efficaciteEnergie, this.resilience, this.fertilite, this.ageFertilite, this.energieEnfant); //energieEnfant en doublon

        //Annulation du retour de la plante créée si une des valeurs du HashMap est fausse
        if ((!(traceInitialisation.get("nomEspece"))) || (!(traceInitialisation.get("besoinEnergie"))) ||
                (!(traceInitialisation.get("efficaciteEnergie"))) || (!(traceInitialisation.get("resilience"))) ||
                (!(traceInitialisation.get("fertilite"))) || (!(traceInitialisation.get("ageFertilite"))) ||
                (!(traceInitialisation.get("energieEnfant")))) {
            return null; //-> lancement d'une exception de ConditionsInitiales?
        }
        return nouvellePlante;
    }

    public void setNomEspece(String nom) {
        if (nom != null) {
            this.nomEspece = nom;

            traceInitialisation.replace("nomEspece", true);
        }
    }

    public void setBesoinEnergie(double besoinEnergie) {
        if (besoinEnergie > 0) {
            this.besoinEnergie = besoinEnergie;

            traceInitialisation.replace("besoinEnergie", true);
        }
    }

    public void setEfficaciteEnergie(double efficaciteEnergie) {
        if ((efficaciteEnergie >= 0) && (efficaciteEnergie <= 1)) {
            this.efficaciteEnergie = efficaciteEnergie;

            traceInitialisation.replace("efficaciteEnergie", true);
        }
    }

    public void setResilience(double resilience) {
        if ((resilience >= 0) && (resilience <= 1)) {
            this.resilience = resilience;

            traceInitialisation.replace("resilience", true);
        }
    }

    public void setFertilite(double fertilite) {
        if ((fertilite >= 0) && (fertilite <= 1)) {
            this.fertilite = fertilite;

            traceInitialisation.replace("fertilite", true);
        }
    }

    public void setAgeFertilite(int ageFertilite) {
        if (ageFertilite >= 0) {
            this.ageFertilite = ageFertilite;

            traceInitialisation.replace("ageFertilite", true);
        }
    }

    public void setEnergieEnfant(double energieEnfant) {
        if (energieEnfant > 0) {
            this.energieEnfant = energieEnfant;

            traceInitialisation.replace("energieEnfant", true);
        }
    }
}

