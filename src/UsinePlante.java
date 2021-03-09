
public class UsinePlante extends Usine {
    public UsinePlante() {
        super();
    }

    protected Plante creerPlante() throws ConditionsInitialesInvalides {
        Plante nouvellePlante = new Plante(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie,
                this.efficaciteEnergie, this.resilience, this.fertilite, this.ageFertilite, this.energieEnfant);

        // Annulation du retour de la plante créée si une des valeurs du HashMap est
        // fausse
        if ((traceInitialisation.containsValue(false))) {
            attributNonInitialise(traceInitialisation);
        }
        return nouvellePlante;
    }

    public void setAll(Plante plante) {
        setNomEspece(plante.getNomEspece());
        setBesoinEnergie(plante.getBesoinEnergie());
        setEfficaciteEnergie(plante.getEfficaciteEnergie());
        setResilience(plante.getResilience());
        setFertilite(plante.getFertilite());
        setAgeFertilite(plante.getAgeFertilite());
        setEnergieEnfant(plante.getEnergieEnfant());
    }
}
