package ppti.model;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Modèle pour gérer la position des joueurs
public class PlayerPositionModel {
    private Map<String, Player> acceptedPlayers;                      // Joueurs acceptés dans la partie
    private List<String> playerOrder;                                 // Ordre des joueurs
    private List<Pair<String, Integer>> chosenPlayersPositions;       // Joueurs et leurs positions choisies
    private int currentPlayerIndex = 0;                               // Index du joueur actuel

    // Constructeur du modèle de position des joueurs
    public PlayerPositionModel(ConcurrentHashMap<String, Player> acceptedPlayers, String firstPlayer) {
        // Copier les joueurs acceptés dans une LinkedHashMap pour préserver l'ordre
        this.acceptedPlayers = new LinkedHashMap<>(acceptedPlayers);
        this.chosenPlayersPositions = new ArrayList<>();
        this.playerOrder = new ArrayList<>();
        reorganizePlayers(firstPlayer); // Réorganise l'ordre des joueurs en commençant par le premier joueur
    }

    // Méthode pour réorganiser les joueurs en fonction du premier joueur
    private void reorganizePlayers(String firstPlayer) {
        List<String> players = new ArrayList<>(acceptedPlayers.keySet());
        int firstPlayerIndex = players.indexOf(firstPlayer);
        for (int i = 0; i < players.size(); i++) {
            int index = (firstPlayerIndex + i) % players.size();
            playerOrder.add(players.get(index));
        }
    }

    // Méthode pour obtenir le nom du joueur actuel
    public String getCurrentPlayerName() {
        if (currentPlayerIndex < playerOrder.size()) {
            return playerOrder.get(currentPlayerIndex);
        }
        return null;
    }

    // Avance à l'indice du prochain joueur
    public void advanceToNextPlayer() {
        currentPlayerIndex++;
    }

    // Vérifie s'il y a un joueur suivant
    public boolean hasNextPlayer() {
        return currentPlayerIndex < playerOrder.size();
    }

    // Ajoute la position choisie par le joueur actuel
    public void addChosenPosition(int position) {
        String playerName = playerOrder.get(currentPlayerIndex);
        chosenPlayersPositions.add(new Pair<>(playerName, position));
    }

    // Retourne la liste des positions choisies par les joueurs
    public List<Pair<String, Integer>> getChosenPlayersPositions() {
        return chosenPlayersPositions;
    }

    // Retourne la map des joueurs acceptés
    public Map<String, Player> getAcceptedPlayers() {
        return acceptedPlayers;
    }

    // Met à jour les joueurs acceptés avec les positions choisies
    public void updateAcceptedPlayersWithPositions() {
        for (Pair<String, Integer> choice : chosenPlayersPositions) {
            String playerName = choice.getKey();
            int chosenPosition = choice.getValue();
            Player player = acceptedPlayers.get(playerName);
            if (player != null) {
                player.setPosition(chosenPosition);
            }
        }
    }

    // Méthode pour trier les positions choisies
    public void sortChosenPlayersPositions() {
        int firstPlayerPosition = chosenPlayersPositions.get(0).getValue();
        chosenPlayersPositions.sort((pair1, pair2) -> {
            int position1 = pair1.getValue();
            int position2 = pair2.getValue();
            int distance1 = (position1 - firstPlayerPosition + 10) % 10;
            int distance2 = (position2 - firstPlayerPosition + 10) % 10;
            return Integer.compare(distance1, distance2);
        });
    }
}
