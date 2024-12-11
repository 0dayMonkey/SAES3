package ppti.model;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

// Modèle représentant la liste des joueurs
public class PlayerListModel {
    private ConcurrentHashMap<String, Player> pendingPlayers;   // Joueurs en attente d'acceptation
    private ConcurrentHashMap<String, Player> acceptedPlayers;  // Joueurs acceptés dans la partie
    private LinkedHashMap<String, String> gameAttributes;       // Attributs du jeu (ex: nombre de joueurs, statut)
    private LinkedHashMap<String, String> botCounts;            // Compte des bots par type
    private int playerCount;        // Nombre total de joueurs dans la partie
    private boolean isFull;         // Indicateur si la partie est complète

    // Constructeur du modèle de la liste des joueurs
    public PlayerListModel(LinkedHashMap<String, String> gameAttributes, LinkedHashMap<String, String> botCounts) {
        this.pendingPlayers = new ConcurrentHashMap<>();
        this.acceptedPlayers = new ConcurrentHashMap<>();
        this.gameAttributes = gameAttributes;
        this.botCounts = botCounts;
        this.playerCount = Integer.parseInt(gameAttributes.get("nbj"));       // Récupère le nombre total de joueurs
        this.isFull = false;  // Initialise l'état de la partie à non pleine
    }

    // Méthode pour ajouter un joueur à la liste des joueurs en attente
    public void addPendingPlayer(Player player) {
        pendingPlayers.put(player.getName(), player);
    }

    // Méthode pour accepter un joueur et le déplacer vers la liste des joueurs acceptés
    public void acceptPlayer(String playerName) {
        Player player = pendingPlayers.remove(playerName);
        if (player != null) {
            acceptedPlayers.put(playerName, player);
        }
    }

    // Méthode pour rejeter un joueur de la liste des joueurs en attente
    public void rejectPlayer(String playerName) {
        pendingPlayers.remove(playerName);
    }

    // Méthode pour exclure un joueur de la liste des joueurs acceptés
    public void excludePlayer(String playerName) {
        acceptedPlayers.remove(playerName);
    }

    // Getter pour obtenir la liste des joueurs en attente
    public ConcurrentHashMap<String, Player> getPendingPlayers() {
        return pendingPlayers;
    }

    // Getter pour obtenir la liste des joueurs acceptés
    public ConcurrentHashMap<String, Player> getAcceptedPlayers() {
        return acceptedPlayers;
    }

    // Getter pour obtenir le nombre total de joueurs
    public int getPlayerCount() {
        return playerCount;
    }

    // Getter pour obtenir le nombre de joueurs acceptés
    public int getAcceptedPlayerCount() {
        return acceptedPlayers.size();
    }

    // Vérifie si la partie est pleine
    public boolean isGameFull() {
        return isFull;
    }

    // Définit si la partie est pleine ou non
    public void setGameFull(boolean isFull) {
        this.isFull = isFull;
    }

    // Getter pour obtenir les attributs du jeu
    public LinkedHashMap<String, String> getGameAttributes() {
        return gameAttributes;
    }

    // Getter pour obtenir le compte des bots
    public LinkedHashMap<String, String> getBotCounts() {
        return botCounts;
    }

    // Compte le nombre de joueurs acceptés ayant une identité spécifique
    public int countAcceptedPlayersByIdentity(String identity) {
        return (int) acceptedPlayers.values().stream()
                .filter(player -> player.getIdentity().equals(identity))
                .count();
    }       
    }


