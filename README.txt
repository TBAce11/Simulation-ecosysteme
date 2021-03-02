1. Suggérez dans votre README.txt deyx façons de garder une trace de quels attributs ont été initialisés. Laquelle avez-
vous choisie et pourquoi?
    1) Implémenter un Hashmap où la clé représente le nom de l'attribut et la valeur est un booléen dont la véracité
    confirme l'initialisation de l'attribut si on est rentré dans le setter de celui-ci, condition importante à vérifier
    puisque les doubles et int par défaut valent 0.0 et 0 respectivement, ce qui respecte dans certains cas les
    contraintes sans précision quant à l'initialisation de l'attribut ou non.

    2) Créer 7 booléens dont la valeur vraie indique que l'attribut a été initialisé et la valeur fausse impliquant le
    contraire. Ceux-ci sont assignés à un seul attribut et initialisés dans le setter de leur attribut respectif en
    testant si ce dernier est égal à "null".

    Nous avons opté pour le Hashmap de par sa simplicité puisqu'il regroupe toutes les valeurs booléennes
    d'initialisation des attributs dans un seul endroit qui s'avère être beaucoup plus pratique à consulter que 7
    variables déclarées dont l'aperçu serait beaucoup trop laborieux et moins  à afficher en terme de code. De plus,
    le Hashmap sera utile comme condition de base imposée lors de la création de plantes tant que toutes ses valeurs
    sont vraies.

