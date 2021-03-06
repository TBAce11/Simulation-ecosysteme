import java.util.HashMap;

public class UsinePlante extends Usine {
    public UsinePlante() {
        super();
    }

    public Plante creerPlante() {
        Plante nouvellePlante = new Plante(this.nomEspece, this.energieEnfant, 0, this.besoinEnergie,
                this.efficaciteEnergie, this.resilience, this.fertilite, this.ageFertilite, this.energieEnfant); //energieEnfant en doublon

        //Annulation du retour de la plante créée si une des valeurs du HashMap est fausse
        if ((traceInitialisation.containsValue(false))){
            return null;
        }
        return nouvellePlante;
    }
}

