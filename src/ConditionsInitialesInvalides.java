/* Auteurs: Anita Abboud et Tarik Benakezouh
Description du fichier: Classe ConditionsInitialesInvalides qui envois des exceptions au programme */

public final class ConditionsInitialesInvalides extends Exception {
    public ConditionsInitialesInvalides(String message) {
        super(message);
    }

    public ConditionsInitialesInvalides(String message, Throwable cause) {
        super(message, cause);
    }
}
