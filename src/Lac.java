/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe Lac qui s'occupent du cycle de vie des organismes. */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public final class Lac {
    private final int energieSolaire;
    private final List<Plante> plantes; // Liste des instances de Plante
    private final List<Herbivore> herbivores; // Liste des instances de Herbivore
    private final List<Carnivore> carnivores; // Liste des instances de Carnivore
    private final List<Animal> animaux = new ArrayList<>(); // Liste des instances de Herbivore et Carnivore réunis

    // Constructeur du Lac à partir des paramètres passés à ConditionsInitiales et des variables locales
    public Lac(int energieSolaire, List<Plante> plantes, List<Herbivore> herbivores, List<Carnivore> carnivores) {
        this.energieSolaire = energieSolaire;
        this.plantes = plantes;
        this.herbivores = herbivores;
        this.carnivores = carnivores;
        this.animaux.addAll(herbivores);
        this.animaux.addAll(carnivores);
    }

    /**
     * Avance la simulation d'un cycle de vie des organismes.
     */
    public void tick() {
        double energieDesPlantes = energieTotale(this.plantes); // Énergie totale des plantes du lac
        double energieAbsorbeePlante; // Énergie absorbée par la plante étudiée
        double energieAbsorbeeAnimal = 0; // Énergie absorbée par l'herbivore ou carnivore étudié
        int energieSupplementairePlante; // Énergie supplémentaire de la plante étudiée
        int energieSupplementaireAnimal; // Énergie supplémentaire de l'herbivore ou carnivore étudié
        int repetitionAlimentation; // Indicatif du nombre de fois qu'un herbivore ou carnivore peut manger

        // Cycle de vie des plantes
        if (plantes.size() > 0) { // Étude des plantes permise seulement si au moins une d'entre elles est vivante
            for (int i = 0; i < plantes.size(); i++) {
                energieAbsorbeePlante = energieAbsorbee(energieDesPlantes, energieSolaire, plantes.get(i).getEnergie());

                // Cas où la plante n'a pas absorbé assez d'énergie solaire pour subvenir à ses besoins énergétiques
                if (plantes.get(i).getBesoinEnergie() > energieAbsorbeePlante) {
                    if (!(plantes.get(i).survie(energieAbsorbeePlante))) {
                        plantes.remove(i);
                        i--; // Ré-organisation de l'index pour éviter de sauter l'étude d'une plante
                        continue; // Étude de la prochaine plante dès la mort de l'actuelle
                    }
                }
                // Cas où la plante a un surplus d'énergie absorbée
                else if (plantes.get(i).getBesoinEnergie() <= energieAbsorbeePlante) {
                    energieSupplementairePlante = energieSupplementairePlante(energieAbsorbeePlante,
                            plantes.get(i).getEnergieEnfant(), plantes.get(i).getBesoinEnergie());
                    // Vérification de l'éligibilité de la plante quant à sa reproduction
                    plantes.get(i).confirmationReproduction(plantes, energieSupplementairePlante);
                }
                if (analyseEnergie(plantes, i)) { // Analyse de l'énergie nulle de la plante théoriquement morte
                    plantes.remove(i);
                    i--;
                }
            }
        }

        // Cycle de vie des herbivores et carnivores
        if (animaux.size() > 0) { // Étude des animaux permise seulement si au moins un d'entre eux est encore vivant
            for (int i = 0; i < animaux.size(); i++) {
                repetitionAlimentation = 0;
                while (Math.random() <= animaux.get(i).getDebrouillardise()) {
                    // Calcul du nombre de fois que l'animal mangera incrémenté de 1 jusqu'à ce que le nombre aléatoire
                    // dépasse la débrouillardise de l'animal actuel
                    repetitionAlimentation++;
                }

                if (repetitionAlimentation > 0) { // Confirmation que l'animal puisse manger
                    energieAbsorbeeAnimal = 0;
                    if (animaux.get(i) instanceof Herbivore) { // Cas où l'animal est un herbivore
                        // Alimentation jusqu'à ce que le nombre de repetitions soit 0 tant qu'une plante soit vivante
                        while (repetitionAlimentation > 0 && (plantes.size() > 0)) {
                            int index;
                            do {
                                index = (int) (Math.random() * (plantes.size() - 1));
                            } while (!(animaux.get(i).getAliments().contains(plantes.get(index).getNomEspece())));
                            // Index calculé tant que celui obtenu pointe vers une espèce de plante ne faisant pas
                            // partie de sa chaîne alimentaire

                            double voracite = voraciteCalcul((Herbivore) animaux.get(i));
                            double energiePerdue = plantes.get(index).transfertEnergie(voracite);

                            // Acquisition de l'énergie perdue de la plante dévorée ajoutée au sein de l'énergie
                            // absorbée de l'animal
                            energieAbsorbeeAnimal += energiePerdue;

                            // Analyse de l'énergie nulle de la plante dévorée si leur liste n'est pas vide
                            if (analyseEnergie(plantes, index) && plantes.size() > 0) {
                                plantes.remove(index);
                            }
                            repetitionAlimentation--;
                        }
                    } else if (animaux.get(i) instanceof Carnivore) { // Cas où l'animal est un carnivore
                        // Alimentation jusqu'à ce que le nombre de répétitions soit 0 tant qu'un autre animal que celui
                        // étudié soit vivant
                        while (repetitionAlimentation > 0 && animaux.size() > 1) {
                            int index;
                            do {
                                index = (int) (Math.random() * (animaux.size() - 1));
                            }while (!(animaux.get(i).getAliments().contains(animaux.get(index).getNomEspece()))
                                    && animaux.get(i).getTailleMaximum() < animaux.get(index).getTailleMaximum());
                            // Index calculé tant que celui obtenu pointe vers une espèce d'animal ne faisant pas partie
                            // de sa chaîne alimentaire et la taille de la proie excède celle du prédateur

                            energieAbsorbeeAnimal += animaux.get(index).getEnergie(); // Énergie de la proie transférée
                            animaux.remove(index); // Mort de l'herbivore dévoré

                            // Change l'ordre d'étude des animaux si l'animal actuel dévore un autre animal avant lui
                            if (index < i){
                                i--;
                            }
                            repetitionAlimentation--;
                        }
                    }
                }

                if (animaux.size() > 0) { // Vérification qu'au moins un animal soit toujours en vie pour l'étudier
                    // Cas où l'animal a absorbé de l'énergie tout en ayant un surplus
                    if (energieAbsorbeeAnimal > 0 && energieAbsorbeeAnimal > animaux.get(i).getBesoinEnergie()) {
                        energieSupplementaireAnimal = (int) (energieAbsorbeeAnimal - animaux.get(i).getBesoinEnergie());
                    } else { // Cas où l'animal n'a absorbé aucune énergie ou s'est rassasié sans surplus
                        energieSupplementaireAnimal = 0;
                    }

                    // Cas où l'animal n'a pas absorbé assez d'énergie pour subvenir à ses besoins énergétiques
                    if (animaux.get(i).getBesoinEnergie() > energieAbsorbeeAnimal) {
                        if (!(animaux.get(i).survie(energieAbsorbeeAnimal))) {
                            animaux.remove(i);
                            i--; // Ré-organisation de l'index pour éviter de sauter l'étude d'un animal
                            continue; // Étude du la prochain animal dès la mort de l'actuel
                        }
                    }
                    // Cas où l'animal a un surplus d'énergie absorbée
                    else if (animaux.get(i).getBesoinEnergie() <= energieAbsorbeeAnimal) {
                        animaux.get(i).confirmationReproduction(animaux, energieSupplementaireAnimal);
                    }
                } else { // Interrupteur de boucle for si tous les herbivores sont morts à la fin de la simulation
                    break;
                }

                // Analyse de l'énergie nulle de l'animal étudié
                if (analyseEnergie(animaux, i)) {
                    animaux.remove(i);
                    i--;
                    continue;
                }
                animaux.get(i).miseAJourTaille(); // Croissance de l'animal étudié
            }
        }
        miseAJourListes(animaux); // Mise à jour du contenu de la liste animaux
    }

    // Méthode qui calcule l'énergie totale des organismes d'une liste mis en argument
    public double energieTotale(List<? extends Organisme> organismes) {
        double energieTotale = 0.0;
        for (int i = 0; i < organismes.size(); i++) {
            energieTotale += organismes.get(i).getEnergie();
        }
        return energieTotale;
    }

    // Méthode qui calcule la portion d'énergie solaire absorbée par la plante étudiée
    public double energieAbsorbee(double energieTotale, double energieSoleil, double energiePlante) {
        if (plantes.size() > 1) {
            return energieSoleil * (energiePlante / energieTotale);
        } else { // Absorption de toute l'énergie solaire par la seule plante survivante
            return energieSoleil;
        }
    }

    // Méthode qui calcule l'énergie supplémentaire de la plante étudiée
    public int energieSupplementairePlante(double energieAbsorbeePlante, double energieActuelle, double besoinEnergie) {
        return (int) ((energieActuelle + energieAbsorbeePlante) - besoinEnergie);
    }

    // Méthode qui retourne une voracité aléatoire situé entre les bornes max et min de la voracité d'un herbivore
    private double voraciteCalcul(Herbivore herbivore) {
        double borneMin = herbivore.getVoraciteMin();
        double borneMax = herbivore.getVoraciteMax();
        return borneMin + (Math.random() * (borneMax - borneMin));
    }

    // Méthode qui retourne un booléen selon la valeur arrondite de l'énergie restant de l'organisme
    private boolean analyseEnergie(List<? extends Organisme> organisme, int i) { //mort de l'organisme suite à la baisse d'énergie
        // On impose la mort de l'organisme si son énergie arrondi est de 0 pour éviter des valeurs négligeables
        if (Math.round(organisme.get(i).getEnergie()) <= 0) {
            return true;
        }
        return false;
    }

    // Méthode qui met à jour les listes d'herbivores et carnivores à partir de la liste d'animaux pour permettre
    // l'impression du rapport pour chaque type d'organisme.
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

    // Méthode qui imprime le rapport final des organismes vivants au sein du lac
    public void imprimeRapport(PrintStream out) {

        // Rapport des plantes
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

        // Rapport des herbivores
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

        // Rapport des carnivores
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
}
