import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes;
    private final List<Herbivore> herbivores;
    private final UsinePlante usinePlanteDuLac = new UsinePlante();
    private final UsineHerbivore usineHerbivoreDuLac = new UsineHerbivore();

    public Lac(int energieSolaire, List<Plante> plantes, List<Herbivore> herbivores) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        this.herbivores = herbivores;
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        double energieDesPlantes = energieTotale(this.plantes);
        double energieAbsorbeeHerbivore = 0;
        double energieAbsorbeePlante;
        int energieSupplementairePlante;
        int energieSupplementaireHerbivore;
        int répétitionAlimentation;

        //Plantes
        if (plantes.size() > 0) {
            for (int i = 0; i < plantes.size(); i++) {
                energieAbsorbeePlante = energieAbsorbee(energieDesPlantes, energieSolaire, plantes.get(i).getEnergie());

                // Si energie manquante
                if (plantes.get(i).getBesoinEnergie() > energieAbsorbeePlante) {
                    if (!(plantes.get(i).survie(energieAbsorbeePlante))) {
                        plantes.remove(i);
                        i--;

                        if ((i == -1) || (i == herbivores.size() - 1)) { //évites de créer un OutOfBoundsException
                            continue;
                        }
                    }
                }
                // Si energie supplementaire
                else if (plantes.get(i).getBesoinEnergie() <= energieAbsorbeePlante) {
                    energieSupplementairePlante = energieSupplementaire(energieAbsorbeePlante, plantes.get(i).getEnergieEnfant(), plantes.get(i).getBesoinEnergie());
                    plantes.get(i).confirmationReproduction(usinePlanteDuLac, plantes, energieSupplementairePlante);
                }
            }
        }
        //energieSupplementaire = 0;

        //Herbivores
        if (herbivores.size() > 0) {
            System.out.println("Liste herbivores :" + herbivores);
            for (int i = 0; i < herbivores.size(); i++) {
                System.out.println();
                System.out.println("Espèce: " + herbivores.get(i).getNomEspece() + ", herbivore #" + (i + 1));
                System.out.println("Age: " + herbivores.get(i).getAge());

                répétitionAlimentation = 0;
                while (Math.random() <= herbivores.get(i).getDebrouillardise()) {
                    répétitionAlimentation++;
                }

                System.out.println("Il va manger: " + répétitionAlimentation + " fois.");
                System.out.println("Energie de base : " + herbivores.get(i).getEnergie());

                if (répétitionAlimentation > 0) {
                    energieAbsorbeeHerbivore = 0;
                    while (répétitionAlimentation > 0) {
                        int index;
                        do {
                            index = (int) (Math.random() * (plantes.size() - 1));
                        } while (!(herbivores.get(i).getAliments().contains(plantes.get(index).getNomEspece())));

                        double voracite = voraciteCalcul(herbivores.get(i));
                        double energiePerdue = plantes.get(index).transfertEnergie(voracite);
                        energieAbsorbeeHerbivore += energiePerdue;

                        répétitionAlimentation--;
                    }
                    System.out.println("Énergie absorbée: " + energieAbsorbeeHerbivore);
                }

                if (energieAbsorbeeHerbivore > 0 && energieAbsorbeeHerbivore > herbivores.get(i).getBesoinEnergie()) {
                    energieSupplementaireHerbivore = (int) (energieAbsorbeeHerbivore - herbivores.get(i).getBesoinEnergie());
                } else {
                    energieSupplementaireHerbivore = 0;
                }

                // Si energie manquante
                if (herbivores.get(i).getBesoinEnergie() > energieAbsorbeeHerbivore) {
                    if (!(herbivores.get(i).survie(energieAbsorbeeHerbivore))) {
                        herbivores.remove(i);
                        i--;

                        System.out.println("Herbivore mort");

                        if ((i == -1) || (i == herbivores.size() - 1)) { //évites de créer un OutOfBoundsException
                            continue;
                        }
                    }
                }
                // Si energie supplementaire
                else if (herbivores.get(i).getBesoinEnergie() <= energieAbsorbeeHerbivore) {
                    System.out.println("Supp calculé (herb): " + energieSupplementaireHerbivore);
                    energieSupplementaireHerbivore = (int) (energieAbsorbeeHerbivore - herbivores.get(i).getBesoinEnergie());
                    herbivores.get(i).confirmationReproduction(usineHerbivoreDuLac, herbivores, energieSupplementaireHerbivore);
                }
                if (herbivores.size() == 0) { //killswitch si tous les herbivores sont morts en pleine simulation
                    break;
                }
            }
            System.out.println();
        }
    }

    public double energieTotale(List<? extends Organisme> organismes){ //y a-t'il un moyen d'utiliser une méthode pour tous les organismes grâce à un upcast?
        double energieTotale = 0.0;
        for (int i = 0; i < organismes.size(); i++){
            energieTotale += organismes.get(i).getEnergie();
        }
        return energieTotale;
    }

    public double energieAbsorbee(double energieTotale, double energieSoleil, double energiePlante){
        return energieSoleil * energiePlante / energieTotale;
    }

    public int energieSupplementaire(double energieAbsorbeePlante, double energieActuelle, double besoinEnergie){
        return (int) ((energieActuelle + energieAbsorbeePlante) - besoinEnergie);
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

    private double voraciteCalcul (Herbivore herbivore){
        double voracite = herbivore.getVoraciteMin() + (Math.random() * (herbivore.getVoraciteMax() - herbivore.getVoraciteMin()));
        return voracite;
    }
}
