import java.io.PrintStream;
import java.util.List;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes;
    private final UsinePlante usineDuLac = new UsinePlante(); //méthode de conception d'un enfant temporaire

    public Lac(int energieSolaire, List<Plante> plantes) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        System.out.println(this.plantes);
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        // À compléter.
        double energieDesPlantes = energieTotale(this.plantes);
        for (int i = 0; i < plantes.size(); i++) {
            double energieAbsorbeePlante = energieAbsorbee(energieDesPlantes, energieSolaire, plantes.get(i).getEnergie());
            // Si energie manquante
            if (plantes.get(i).getBesoinEnergie() > energieAbsorbeePlante) {
                if (!(plantes.get(i).survie(energieAbsorbeePlante))) {
                    plantes.remove(i);
                }
            }
            // Si energie supplementaire -> que se passe-t-il quand
            if (plantes.get(i).getBesoinEnergie() < energieAbsorbeePlante) {
                double energieSupplementaire = energieSupplementaire(energieAbsorbeePlante, plantes.get(i).getBesoinEnergie());

                if (plantes.get(i).getAge() >= plantes.get(i).getAgeFertilite()) { //condition placée trop tôt ou non?
                    while (energieSupplementaire > 0) {
                        //reproduction retourne énergie supplémentaire tout en effectuant les opérations nécessaires
                        energieSupplementaire = plantes.get(i).reproduction(usineDuLac, this.plantes, i, energieSupplementaire);
                    }
                }
            }
        }
    }

    public double energieTotale(List<Plante> plantes){
        double energieTotale = 0.0;
        for (int i = 0; i < plantes.size(); i++){
            energieTotale += plantes.get(i).getEnergie();
        }
        return energieTotale;
    }
    public double energieAbsorbee(double energieTotale, double energieSoleil, double energiePlante){
        return energieSoleil*energiePlante/energieTotale;
    }

    public double energieSupplementaire(double energieAbsorbeePlante, double besoinEnergie){
        return (energieAbsorbeePlante - besoinEnergie);
    }

    public void imprimeRapport(PrintStream out) {
        var especes = this.plantes.stream().collect(groupingBy(
                Plante::getNomEspece,
                summarizingDouble(Plante::getEnergie)));
        out.println("Il reste " + especes.size() + " espèces de plantes.");
        for (var entry : especes.entrySet()) {
            var value = entry.getValue();
            out.printf("%s: %d individus qui contiennent en tout %.2f unités d'énergie.",
                    entry.getKey(),
                    value.getCount(),
                    value.getSum());
        }
    }
}
