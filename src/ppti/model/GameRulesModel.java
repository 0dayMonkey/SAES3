package ppti.model;

// Modèle contenant le texte des règles du jeu
public class GameRulesModel {
    private final String rulesText; // Texte des règles du jeu

    // Constructeur qui initialise le texte des règles
    public GameRulesModel() {
        this.rulesText = """
        <u><h2>Objectif du jeu : </h2></u>
        
        Chaque joueur reçoit 12 cartes et une partie se déroule en 12 tours, correspondant au nombre de cartes par joueur. Le but est d'éviter de perdre les cartes bouées à la fin de chaque pli, (une carte bouée représente un point de victoire). 

        <br><br>

        <u><h2>Règles de base : </h2></u>

        <u><h3>Les cartes et les tours : </h3></u>
        
        Chaque joueur commence avec 12 cartes en main. À chaque tour, deux cartes marée (numérotées de 1 à 12, en double exemplaire pour un total de 24 cartes marée) sont tirées au hasard. Ces deux cartes marée constituent les valeurs en jeu pour ce tour. 

        <br>

        <u><h3>Déroulement d’un tour : </u></h3>
        
        Chaque joueur choisit une carte de sa main et la révèle simultanément avec les autres joueurs. Le joueur ayant joué la carte la plus élevée prend la carte marée avec la plus faible valeur, tandis que le deuxième joueur ayant la carte la plus forte prend l’autre carte. 

        <br>
        
        Exemple : Si les deux cartes marées tirées sont 5 et 9, et que les joueurs révèlent les cartes suivantes : 32, 45, 60, 22, et 40, alors le joueur ayant joué la carte 60 prendra la carte marée 5, et celui ayant joué la carte 45 prendra la carte marée 9. 

        <br>
        
        <u><h3>Perte de bouée : </u></h3>
        
        À la fin de chaque tour, le joueur ayant la carte marée la plus élevée sur la table perd une bouée. Une bouée correspond à un point de victoire. Si un joueur n'a plus de bouées, il reste tout de même en jeu, mais dès qu'il perd à nouveau une bouée (même avec 0 bouée), il est éliminé. 

        <br>

        <u><h3>Récupération des cartes marée : </u></h3>
        
        Lorsque les deux nouvelles cartes marée sont tirées, si l’un des joueurs récupère une carte marée identique à celle qu'il possède déjà, la nouvelle recouvre l'ancienne. 

        <br>
        
        <u><h3>Fin de la manche et calcul des points : </u></h3>
        
        Après 12 tours, la manche prend fin. Les points sont calculés en fonction du nombre de bouées restantes pour chaque joueur, une bouée équivalant à un point. À la fin de chaque manche, les joueurs échangent leurs cartes avec le joueur à leur gauche et entament une nouvelle manche. 

        <br>
        
        Le nombre de manches à jouer est égal au nombre de joueurs. À la fin de toutes les manches, les points cumulés de chaque joueur sont comptés, et celui avec le score le plus élevé remporte la partie.  
        """;
    }

    // Méthode pour obtenir le texte des règles
    public String getRulesText() {
        return rulesText;
    }
}
