package ppti.presenter;
/*
 * BROUILLON  
import javafx.util.Pair;
import ppti.model.PlayerPositionModel;
import ppti.view.GameInterfacePptiView;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class GameInterfacePptiPresenter {
    private GameInterfacePptiView view;
    private PlayerPositionModel positionModel;
    private int playerCount;

    public GameInterfacePptiPresenter(GameInterfacePptiView view, PlayerPositionModel positionModel) {
        this.view = view;
        this.positionModel = positionModel;
    }

    // Méthode pour configurer l'interface en fonction des positions choisies
    public void configureGameInterface() {
        // Récupère les positions choisies
        List<Pair<String, Integer>> chosenPositions = positionModel.getChosenPlayersPositions();
        
        // Mapping des positions aux emplacements de joueurs
        Map<Integer, String> positionToPlayerName = new HashMap<>();
        for (Pair<String, Integer> position : chosenPositions) {
            positionToPlayerName.put(position.getValue(), position.getKey());
        }

        // Configurer le nombre de joueurs
        this.playerCount = chosenPositions.size();
        view.setNumberOfPlayers(playerCount);

        // Placement personnalisé des joueurs selon leurs positions
        switch(playerCount) {
            case 3:
                placePlayers3(positionToPlayerName);
                break;
            case 4:
                placePlayers4(positionToPlayerName);
                break;
            case 5:
                placePlayers5(positionToPlayerName);
                break;
        }
    }

    private void placePlayers3(Map<Integer, String> positionToPlayerName) {
        // Logique de placement pour 3 joueurs
        // Exemple (à adapter selon votre logique de placement)
        if (positionToPlayerName.containsKey(1)) 
            view.setPlayerName(1, positionToPlayerName.get(1));
        if (positionToPlayerName.containsKey(2)) 
            view.setPlayerName(2, positionToPlayerName.get(2));
        if (positionToPlayerName.containsKey(3)) 
            view.setPlayerName(3, positionToPlayerName.get(3));
    }

    private void placePlayers4(Map<Integer, String> positionToPlayerName) {
        // Logique de placement pour 4 joueurs
        // Exemple (à adapter selon votre logique de placement)
        if (positionToPlayerName.containsKey(1)) 
            view.setPlayerName(1, positionToPlayerName.get(1));
        if (positionToPlayerName.containsKey(2)) 
            view.setPlayerName(2, positionToPlayerName.get(2));
        if (positionToPlayerName.containsKey(3)) 
            view.setPlayerName(3, positionToPlayerName.get(3));
        if (positionToPlayerName.containsKey(4)) 
            view.setPlayerName(4, positionToPlayerName.get(4));
    }

    private void placePlayers5(Map<Integer, String> positionToPlayerName) {
        // Logique de placement pour 5 joueurs
        // Exemple (à adapter selon votre logique de placement)
        if (positionToPlayerName.containsKey(1)) 
            view.setPlayerName(1, positionToPlayerName.get(1));
        if (positionToPlayerName.containsKey(2)) 
            view.setPlayerName(2, positionToPlayerName.get(2));
        if (positionToPlayerName.containsKey(3)) 
            view.setPlayerName(3, positionToPlayerName.get(3));
        if (positionToPlayerName.containsKey(4)) 
            view.setPlayerName(4, positionToPlayerName.get(4));
        if (positionToPlayerName.containsKey(5)) 
            view.setPlayerName(5, positionToPlayerName.get(5));
    }

    // Getter pour le nombre de joueurs
    public int getPlayerCount() {
        return playerCount;
    }
}*/







