import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes;
    private final List<Herbivore> herbivores;
    private final List<Carnivore> carnivores;
    private final List<Animal> animaux = new ArrayList<>();

    public Lac(int energieSolaire, List<Plante> plantes, List<Herbivore> herbivores, List<Carnivore> carnivores) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        this.herbivores = herbivores;
        this.carnivores = carnivores;
        this.animaux.addAll(herbivores);
        this.animaux.addAll(carnivores);
    }

    /**
     * Avance la simulation d'un cycle.
     */
    public void tick() {
        double energieDesPlantes = energieTotale(this.plantes);
        double energieAbsorbeePlante = 0;
        double energieAbsorbeeAnimal = 0;
        int energieSupplementairePlante;
        int energieSupplementaireAnimal;
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
                        continue;
                    }
                }
                // Si energie supplementaire
                else if (plantes.get(i).getBesoinEnergie() <= energieAbsorbeePlante) {
                    energieSupplementairePlante = energieSupplementairePlante(energieAbsorbeePlante, plantes.get(i).getEnergieEnfant(), plantes.get(i).getBesoinEnergie());
                    plantes.get(i).confirmationReproduction(plantes, energieSupplementairePlante);
                }
                if (analyseEnergie(plantes, i)) {
                    plantes.remove(i);
                }
            }
        }

        //Herbivores et carnivores
        if (animaux.size() > 0) {
            for (int i = 0; i < animaux.size(); i++) {
                répétitionAlimentation = 0;
                while (Math.random() <= animaux.get(i).getDebrouillardise()) {
                    répétitionAlimentation++;
                }

                if (répétitionAlimentation > 0) {
                    energieAbsorbeeAnimal = 0;
                    if (animaux.get(i) instanceof Herbivore) {
                        while (répétitionAlimentation > 0 && (plantes.size() > 0)) {
                            int index;
                            do {
                                index = (int) (Math.random() * (plantes.size() - 1));
                            } while (!(animaux.get(i).getAliments().contains(plantes.get(index).getNomEspece())));

                            double voracite = voraciteCalcul((Herbivore) animaux.get(i));
                            double energiePerdue = plantes.get(index).transfertEnergie(voracite);
                            energieAbsorbeeAnimal += energiePerdue;

                            if (analyseEnergie(plantes, index) && plantes.size() > 0) {
                                plantes.remove(index);
                            }
                            répétitionAlimentation--;
                        }
                    } else if (animaux.get(i) instanceof Carnivore) {
                        while (répétitionAlimentation > 0 && animaux.size() > 0) {
                            int index;
                            do {
                                index = (int) (Math.random() * (animaux.size() - 1));
                            }while (!(animaux.get(i).getAliments().contains(animaux.get(index).getNomEspece())) && animaux.get(i).getTailleMaximum() < animaux.get(index).getTailleMaximum());

                            energieAbsorbeeAnimal += animaux.get(index).getEnergie();
                            animaux.remove(index); //mort de l'herbivore dévoré
                            if (index < i){ //change l'ordre d'étude des animaux si l'animal actuel dévore un autre animal avant lui
                                i--;
                            }
                            répétitionAlimentation--;
                        }
                    }
                }

                if (animaux.size() > 0) { //vérification qu'au moins un animal est toujours en vie
                    if (energieAbsorbeeAnimal > 0 && energieAbsorbeeAnimal > animaux.get(i).getBesoinEnergie()) {
                        energieSupplementaireAnimal = (int) (energieAbsorbeeAnimal - animaux.get(i).getBesoinEnergie());
                    } else {
                        energieSupplementaireAnimal = 0;
                    }

                    // Si énergie manquante
                    if (animaux.get(i).getBesoinEnergie() > energieAbsorbeeAnimal) {
                        if (!(animaux.get(i).survie(energieAbsorbeeAnimal))) {
                            animaux.remove(i);
                            i--;
                            continue;
                        }
                    }
                    // Si energie supplémentaire
                    else if (animaux.get(i).getBesoinEnergie() <= energieAbsorbeeAnimal) {
                        animaux.get(i).confirmationReproduction(animaux, energieSupplementaireAnimal);
                    }
                } else { //killswitch si tous les herbivores sont morts à la fin de la simulation
                    break;
                }

                if (analyseEnergie(animaux, i)) {
                    animaux.remove(i);
                    i--;
                    continue;
                }
                animaux.get(i).miseAJourTaille();
            }
        }

        //Carnivores
        /*if (carnivores.size() > 0) {
            for (int i = 0; i < carnivores.size(); i++) {
                répétitionAlimentation = 0;
                while (Math.random() <= carnivores.get(i).getDebrouillardise()) {
                    répétitionAlimentation++;
                }

                if (répétitionAlimentation > 0) {
                    energieAbsorbeeCarnivore = 0;
                    while (répétitionAlimentation > 0 && herbivores.size() > 0) {
                        int index;

                        do {
                            index = (int) (Math.random() * (herbivores.size() - 1));
                        } while (!(carnivores.get(i).getAliments().contains(herbivores.get(index).getNomEspece())) && carnivores.get(i).getTailleMaximum() < herbivores.get(index).getTailleMaximum());

                        energieAbsorbeeCarnivore += herbivores.get(index).getEnergie();
                        herbivores.remove(index); //mort de l'herbivore dévoré

                        répétitionAlimentation--;
                    }
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

                        if ((i == -1) || (i == carnivores.size() - 1)) { //évites de créer un OutOfBoundsException
                            continue;
                        }
                    }
                }
                // Si energie supplementaire
                else if (carnivores.get(i).getBesoinEnergie() <= energieAbsorbeeCarnivore) {
                    carnivores.get(i).confirmationReproduction(carnivores, energieSupplementaireCarnivore);
                }
                if (carnivores.size() == 0) { //killswitch si tous les carnivores sont morts en pleine simulation
                    break;
                }
                carnivores.get(i).miseAJourTaille();
                if(analyseEnergie(carnivores, i)){
                    carnivores.remove(i);
                }
            }
        }*/
        miseAJourListes(animaux);
    }

    public double energieTotale(List<? extends Organisme> organismes) { //y a-t'il un moyen d'utiliser une méthode pour tous les organismes grâce à un upcast?
        double energieTotale = 0.0;
        for (int i = 0; i < organismes.size(); i++) {
            energieTotale += organismes.get(i).getEnergie();
        }
        return energieTotale;
    }

    public double energieAbsorbee(double energieTotale, double energieSoleil, double energiePlante) {
        if (plantes.size() > 1) {
            return energieSoleil * (energiePlante / energieTotale);
        } else {
            return energieSoleil;
        }
    }

    public int energieSupplementairePlante(double energieAbsorbeePlante, double energieActuelle, double besoinEnergie) {
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

    private double voraciteCalcul(Herbivore herbivore) {
        double voracite = herbivore.getVoraciteMin() + (Math.random() * (herbivore.getVoraciteMax() - herbivore.getVoraciteMin()));
        return voracite;
    }

    private boolean analyseEnergie(List<? extends Organisme> organisme, int i) { //mort de l'organisme suite à la baisse d'énergie
        if (Math.round(organisme.get(i).getEnergie()) <= 0) {
            return true;
        }
        return false;
    }

    private void miseAJourListes(List<Animal> animaux) {
        this.herbivores.clear();
        this.carnivores.clear();
        for (int i = 0; i < animaux.size(); i++) {
            if (animaux.get(i) instanceof Herbivore) {
                this.herbivores.add((Herbivore) animaux.get(i));
            } else if (animaux.get(i) instanceof Carnivore) {
                this.carnivores.add((Carnivore) animaux.get(i));
            }
        }
    }
}
