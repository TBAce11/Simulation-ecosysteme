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


2. Expliquer dans votre README.txt comment les responsabilités entre les classes Lac et Plante ont été divisées et
pourquoi. Quels changements avez-vous vû faire?

De prime abord, les opérations liées aux instances de Plante ont été effectuées au sein de cette classe afin de
faciliter la lecture et réduire drastiquement la quantité du code qui serait franchement douloureux à suivre en
éxagérant l'utilisation de la liste "plantes" afin d'accéder aux valeurs de l'instance à la position i de l'Arraylist.
La séparation du code a donc été effectuée dès que les opérations du simulateur faisaient appel aux attributs de
l'instance de Plante manipulée, tri crucial lorsque ceux-ci subissait une modification sans paramètre donné puisque
accomplir ceci au sein de Lac impliquerait l'utilisation illégale de setters. Les changements apportés aux codes
ont simplement consisté en un passage en paramètre d'objets tels que l'usine du lac et l'Arraylist plantes de plus que
des variables locales nécessaire aux calculs de probabilité d'un scénario et de fluctuations d'énergie.

3. Créer une classe ou une interface nommée Organisme. Modifier nos deux classes pour hériter de la classe Organisme ou
d'implanter l'interface Organisme selon ce que vous avez choisi. Expliquez votre choix dans le fichier README.txt.

Nous avons opté pour la classe au lieu de l'interface puisque nous ne pouvons pas concevoir le fait que les êtres
vivants générés dans Lac puissent se limiter à des méthodes abtraites se limitant à décrire leurs actions comme une
sorte de guide d'utilisateur. Une plante et un herbivore sont tous des organismes héritant des mêmes attributs et
méthodes et leurs différences sont implémentées dans leurs propres classes uniques, rendant une interface inutile vu
l'absence d'un conflit d'héritage.

4. Il y a aussi de la répétition entre les deux classes-usines. Expliquez aussi dans le README.txt comment l'éliminer
et faites les changements proposés.

5.Expliquez dans votre README.txt comment vous avez séparé les responsabilités entre Lac et Herbivore. Avez-vous changé
de stratégie par rapport à la première partie?

6. Avez-vous réutilisé du code? Si oui, expliquer comment et pourquoi c'est désirable dans votre README.txt. Si non,
expliquez pourquoi c'est indésirable.




