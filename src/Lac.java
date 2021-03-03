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
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        double energieDesPlantes = energieTotale(this.plantes);
        for (int i = 0; i < plantes.size(); i++) {
            System.out.println("Espèce: " + plantes.get(i).getNomEspece() + ", plante #" + (i + 1));
            double energieAbsorbeePlante = energieAbsorbee(energieDesPlantes, energieSolaire, plantes.get(i).getEnergie());

            // Si energie manquante
            if (plantes.get(i).getBesoinEnergie() > energieAbsorbeePlante) {
                if (!(plantes.get(i).survie(energieAbsorbeePlante))) {
                    plantes.remove(i);
                    //i--;
                    System.out.println("Plante morte");
                }
            }
            // Si energie supplementaire
            else if (plantes.get(i).getBesoinEnergie() <= energieAbsorbeePlante) {
                int energieSupplementaire = energieSupplementaire(energieAbsorbeePlante, plantes.get(i).getEnergieEnfant(), plantes.get(i).getBesoinEnergie());
                System.out.println("Supp calculé: " + energieSupplementaire);
                plantes.get(i).confirmationReproduction(usineDuLac, plantes, energieSupplementaire);
            }
            System.out.println();
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

    public int energieSupplementaire(double energieAbsorbeePlante, double energieActuelle, double besoinEnergie){
        return (int) ((energieActuelle+ energieAbsorbeePlante) - besoinEnergie);
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
