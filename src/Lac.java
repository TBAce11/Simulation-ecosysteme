import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes;
    private final List<Herbivore> herbivores;
    private final List<Carnivore> carnivores;
    private final UsinePlante usinePlanteDuLac = new UsinePlante();
    private final UsineHerbivore usineHerbivoreDuLac = new UsineHerbivore();
    private final  UsineCarnivore usineCarnivoreDuLac = new UsineCarnivore();

    public Lac(int energieSolaire, List<Plante> plantes, List<Herbivore> herbivores, List<Carnivore> carnivores) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        this.herbivores = herbivores;
        this.carnivores = carnivores;
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        double energieDesPlantes = energieTotale(this.plantes);
        double energieAbsorbeePlante = 0;
        double energieAbsorbeeHerbivore = 0;
        double energieAbsorbeeCarnivore = 0;
        int energieSupplementairePlante;
        int energieSupplementaireHerbivore;
        int energieSupplementaireCarnivore;
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
                    energieSupplementairePlante = energieSupplementairePlante(energieAbsorbeePlante, plantes.get(i).getEnergieEnfant(), plantes.get(i).getBesoinEnergie());
                    plantes.get(i).confirmationReproduction(usinePlanteDuLac, plantes, energieSupplementairePlante);
                }
            }
        }
        //energieSupplementaire = 0;

        //Herbivores
        if (herbivores.size() > 0) {
            for (int i = 0; i < herbivores.size(); i++) {
                répétitionAlimentation = 0;
                while (Math.random() <= herbivores.get(i).getDebrouillardise()) {
                    répétitionAlimentation++;
                }

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
                        if ((i == -1) || (i == herbivores.size() - 1)) { //évites de créer un OutOfBoundsException
                            continue;
                        }
                    }
                }
                // Si energie supplementaire
                else if (herbivores.get(i).getBesoinEnergie() <= energieAbsorbeeHerbivore) {
                    herbivores.get(i).confirmationReproduction(usineHerbivoreDuLac, herbivores, energieSupplementaireHerbivore);
                }

                if (herbivores.size() == 0) { //killswitch si tous les herbivores sont morts en pleine simulation
                    break;
                }
            }
        }

        //Carnivores
        if (carnivores.size() > 0) {
            System.out.println("Liste carnivores :" + carnivores);
            for (int i = 0; i < carnivores.size(); i++) {
                System.out.println();
                System.out.println("Espèce: " + carnivores.get(i).getNomEspece() + ", carnivore #" + (i + 1));
                System.out.println("Age: " + carnivores.get(i).getAge());

                répétitionAlimentation = 0;
                while (Math.random() <= carnivores.get(i).getDebrouillardise()) {
                    répétitionAlimentation++;
                }

                System.out.println("Il va manger: " + répétitionAlimentation + " fois.");
                System.out.println("Energie de base : " + carnivores.get(i).getEnergie());

                if (répétitionAlimentation > 0) {
                    energieAbsorbeeCarnivore = 0;
                    System.out.println("Taille de la liste d'herbivores avant: " + herbivores.size());
                    while (répétitionAlimentation > 0 && herbivores.size() > 0) {
                        int index;

                        do {
                            index = (int) (Math.random() * (herbivores.size() - 1));
                        } while (!(carnivores.get(i).getAliments().contains(herbivores.get(index).getNomEspece())));

                        energieAbsorbeeCarnivore += herbivores.get(index).getEnergie();
                        System.out.println("Herbivore #" + index + " de l'espèce <<" + herbivores.get(index).getNomEspece() + ">> valant une énergie de " + herbivores.get(index).getEnergie() + " dévoré.");
                        herbivores.remove(index); //mort de l'herbivore dévoré

                        répétitionAlimentation--;
                    }
                    System.out.println("Taille de la liste d'herbivores après: " + herbivores.size());
                    System.out.println("Énergie absorbée: " + energieAbsorbeeCarnivore);
                }

                if (energieAbsorbeeCarnivore > 0 && energieAbsorbeeCarnivore > carnivores.get(i).getBesoinEnergie()) {
                    energieSupplementaireCarnivore = (int) (energieAbsorbeeCarnivore - carnivores.get(i).getBesoinEnergie());
                } else {
                    energieSupplementaireCarnivore = 0;
                }

                // Si energie manquante
                if (carnivores.get(i).getBesoinEnergie() > energieAbsorbeeCarnivore) {
                    if (!(carnivores.get(i).survie(energieAbsorbeeCarnivore))) {
                        carnivores.remove(i);
                        i--;
                        System.out.println("Carnivore mort");

                        if ((i == -1) || (i == carnivores.size() - 1)) { //évites de créer un OutOfBoundsException
                            continue;
                        }
                    } else {
                        System.out.println("Le carnivore a échappé à la mort.");
                    }
                }
                // Si energie supplementaire
                else if (carnivores.get(i).getBesoinEnergie() <= energieAbsorbeeCarnivore) {
                    System.out.println("Supp calculé (carni): " + energieSupplementaireCarnivore);
                    carnivores.get(i).confirmationReproduction(usineCarnivoreDuLac, carnivores, energieSupplementaireCarnivore);
                    System.out.println("Énergie totale: " + carnivores.get(i).getEnergie());
                }
                if (carnivores.size() == 0) { //killswitch si tous les carnivores sont morts en pleine simulation
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

    public int energieSupplementairePlante(double energieAbsorbeePlante, double energieActuelle, double besoinEnergie){
        return (int) ((energieActuelle + energieAbsorbeePlante) - besoinEnergie);
    }

    public void imprimeRapport(PrintStream out) {
        var especesPlantes = this.plantes.stream().collect(groupingBy(
                Plante::getNomEspece,
                summarizingDouble(Plante::getEnergie)));
        out.println("Il reste " + especesPlantes.size() + " espèces de plantes.");
        for (var entry : especesPlantes.entrySet()) {
            var value = entry.getValue();
            out.printf("%s: %d individus qui contiennent en tout %.2f unités d'énergie.",
                    entry.getKey(),
                    value.getCount(),
                    value.getSum());
            System.out.println();
        }

        var especesHerbivores = this.herbivores.stream().collect(groupingBy(
                Herbivore::getNomEspece,
                summarizingDouble(Herbivore::getEnergie)));
        out.println("Il reste " + especesHerbivores.size() + " espèces de herbivores.");
        for (var entry : especesHerbivores.entrySet()) {
            var value = entry.getValue();
            out.printf("%s: %d individus qui contiennent en tout %.2f unités d'énergie.",
                    entry.getKey(),
                    value.getCount(),
                    value.getSum());
            System.out.println();
        }

        var especesCarnivores = this.carnivores.stream().collect(groupingBy(
                Carnivore::getNomEspece,
                summarizingDouble(Carnivore::getEnergie)));
        out.println("Il reste " + especesCarnivores.size() + " espèces de carnivores.");
        for (var entry : especesCarnivores.entrySet()) {
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
