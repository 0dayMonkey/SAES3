package ppti.presenter;


import ppti.model.GameConfigModel;
import ppti.model.PartieModel;
import ppti.model.PlayerListModel;
import ppti.model.Player;
import ppti.model.Card;

public class PartiePresenter {
    private PartieModel partieModel;             // Données de la partie
    private GameConfigModel gameConfig;          // Configuration du jeu
    private PlayerListModel playerListModel;     // Gestion des joueurs

    // Constructeur
    public PartiePresenter(GameConfigModel gameConfig, PlayerListModel playerListModel) {
        this.partieModel = new PartieModel();    // Initialise une nouvelle partie
        this.gameConfig = gameConfig;
        this.playerListModel = playerListModel;
    }

  
    // Incrémente le nombre de plis
    public void incrementerPli() {
        int nouveauPli = partieModel.getPli() + 1;
        partieModel.setPli(nouveauPli);
    }

    // Permet à un joueur de jouer une carte
    public void jouerCarte(String playerName, Card carte) {
        // Récupère le joueur dans la liste des joueurs acceptés
        Player joueur = playerListModel.getAcceptedPlayers().get(playerName);
        if (joueur != null) {
            // Vérifie si le joueur possède déjà cette carte
            if (!partieModel.joueurPossedeCarte(joueur, carte)) {
                partieModel.ajouterCartePourJoueur(joueur, carte);   // Ajoute la carte au joueur
                partieModel.ajouterCarteJouee(carte);                // Ajoute la carte aux cartes jouées
            } else {
                throw new IllegalArgumentException("Cette carte a déjà été jouée par ce joueur.");
            }
        } else {
            throw new IllegalArgumentException("Le joueur n'existe pas ou n'a pas été accepté.");
        }
    }

    // Passe à la manche suivante
    public void changerManche() {
        int nouvelleManche = partieModel.getMancheActuelle() + 1;
        partieModel.setMancheActuelle(nouvelleManche);
        partieModel.reinitialiserCartesJouees();               // Réinitialise les cartes jouées
        partieModel.reinitialiserCartesMareesActuelles();      // Réinitialise les cartes marées actuelles
    }

    // Ajoute une carte marée à la liste totale
    public void ajouterCarteMareesTotal(Card carte) {
        partieModel.ajouterCarteMareesTotal(carte);
    }

    // Ajoute une carte marée à la liste des cartes actuelles
    public void ajouterCarteMareesActuelles(Card carte) {
        partieModel.ajouterCarteMareesActuelles(carte);
    }

    // Getter pour le nom de la partie
    public String getNomPartie() {
        return gameConfig.getPartName(); // Récupère le nom de partie depuis GameConfigModel
    }

    // Getters pour les modèles
    public PartieModel getPartieModel() {
        return partieModel;
    }

    public PlayerListModel getPlayerListModel() {
        return playerListModel;
    }
}


