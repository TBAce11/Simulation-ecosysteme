/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe parent Usine qui s'étend aux classes-usines enfants. */

import java.util.HashMap;
import java.util.Iterator;

public abstract class Usine {
    protected String nomEspece;
    protected double besoinEnergie;
    protected double efficaciteEnergie;
    protected double resilience;
    protected double fertilite;
    protected int ageFertilite;
    protected double energieEnfant;

    HashMap<String, Boolean> traceInitialisation = new HashMap<String, Boolean>(); /* Dictionnaire qui servira de trace pour s'assurer
    que tous les setters de l'usine ont été appelés avant la création d'un organisme. */

    // Constructeur d'une Usine qui servira de schéma pour ses trois sous-classes
    // Création d'une trace spécifiant que les attributs n'ont pas été initialisés par défaut
    public Usine() {
        traceInitialisation.put("nomEspece", false);
        traceInitialisation.put("besoinEnergie", false);
        traceInitialisation.put("efficaciteEnergie", false);
        traceInitialisation.put("resilience", false);
        traceInitialisation.put("fertilite", false);
        traceInitialisation.put("ageFertilite", false);
        traceInitialisation.put("energieEnfant", false);
    }

    protected void setNomEspece(String nom) {
        if (nom != null) {
            this.nomEspece = nom;
            traceInitialisation.replace("nomEspece", true);
        }
    }

    protected void setBesoinEnergie(double besoinEnergie) {
        if (besoinEnergie > 0) {
            this.besoinEnergie = besoinEnergie;
            traceInitialisation.replace("besoinEnergie", true);
        }
    }

    protected void setEfficaciteEnergie(double efficaciteEnergie) {
        if ((efficaciteEnergie >= 0) && (efficaciteEnergie <= 1)) {
            this.efficaciteEnergie = efficaciteEnergie;
            traceInitialisation.replace("efficaciteEnergie", true);
        }
    }

    protected void setResilience(double resilience) {
        if ((resilience >= 0) && (resilience <= 1)) {
            this.resilience = resilience;
            traceInitialisation.replace("resilience", true);
        }
    }

    protected void setFertilite(double fertilite) {
        if ((fertilite >= 0) && (fertilite <= 1)) {
            this.fertilite = fertilite;
            traceInitialisation.replace("fertilite", true);
        }
    }

    protected void setAgeFertilite(int ageFertilite) {
        if (ageFertilite >= 0) {
            this.ageFertilite = ageFertilite;
            traceInitialisation.replace("ageFertilite", true);
        }
    }

    protected void setEnergieEnfant(double energieEnfant) {
        if (energieEnfant > 0) {
            this.energieEnfant = energieEnfant;
            traceInitialisation.replace("energieEnfant", true);
        }
    }

    // Méthode qui renvoit une exception lorsque un ou plusieurs attributs de l'usine n'ont pas été spécifiés
    protected void attributNonInitialise(HashMap traceInitialisation) throws ConditionsInitialesInvalides {
        Iterator it = traceInitialisation.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry paire = (HashMap.Entry) it.next();
            if (paire.getValue().equals(false)) {
                throw new ConditionsInitialesInvalides("L'attribut \"" + paire.getKey()
                        + "\" n'a pas été intialisé suite à un non-respect de sa contrainte ou son absence.");
            }
        }
    }
}
