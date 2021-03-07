import java.util.HashSet;
import java.util.Set;

public abstract class Animal extends Organisme{
    private int tailleMaximum;
    private double debrouillardise;
    private Set<String> aliments = new HashSet<String>();

    public Animal(String nomEspece, double energie, int age, double besoinEnergie, double efficaciteEnergie, double
            resilience, double fertilite, int ageFertilite, double energieEnfant, int tailleMaximum, double debrouillardise, Set <String> aliments){
        super(nomEspece, energie, age, besoinEnergie, efficaciteEnergie, resilience, fertilite, ageFertilite, energieEnfant);
        this.tailleMaximum = tailleMaximum;
        this.debrouillardise = debrouillardise;
        this.aliments = aliments;
    }

}
