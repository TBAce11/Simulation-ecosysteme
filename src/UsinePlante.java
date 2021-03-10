/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe enfant UsinePlante qui étend la classe parent Usine. */

public class UsinePlante extends Usine {

    // Constructeur d'une usine de plantes
    public UsinePlante() {
        super(); // Appel au constructeur d'une usine
    }

    // Méthode qui appelle le constructeur d'une plante et qui lui transmet ses attributs
    protected Plante creerPlante() throws ConditionsInitialesInvalides {
        // Évalue la présence d'attributs non-initialisés dans la trace de l'usine et envoie une exception si nécessaire
        if ((traceInitialisation.containsValue(false))) {
            attributNonInitialise(traceInitialisation);
        }
        Plante nouvellePlante = new Plante(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie,
                this.efficaciteEnergie, this.resilience, this.fertilite, this.ageFertilite, this.energieEnfant);
        return nouvellePlante;
    }

    // Méthode qui initialise tous les attributs de l'usine à partir d'un plante mis en argument
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
