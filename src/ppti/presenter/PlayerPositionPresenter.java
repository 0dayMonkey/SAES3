package ppti.presenter;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import ppti.data.Data;
import ppti.model.Player;
import ppti.model.PlayerListModel;
import ppti.model.PlayerPositionModel;
import ppti.view.PlayerPositionView;
import ppti.view.GameInterfacePptiView;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

// Présentateur pour la gestion des positions des joueurs
public class PlayerPositionPresenter {
    private PlayerPositionModel model;           // Le modèle contenant les données sur les positions des joueurs
    private PlayerPositionView view;             // La vue associée pour l'affichage
    private Stage primaryStage;                  // La scène principale de l'application
    private Set<Integer> takenPositions = new HashSet<>(); // Ensemble des positions déjà prises
    private PlayerListModel partiemodel;         // Modèle de la partie (liste des joueurs)

    // Constructeur du présentateur
    public PlayerPositionPresenter(PlayerPositionModel model, PlayerPositionView view, Stage primaryStage, PlayerListModel partiemodel) {
        this.model = model;
        this.partiemodel = partiemodel;
        this.view = view;
        this.primaryStage = primaryStage;
        initialize(); // Initialisation des composants et des actions
    }

    // Méthode pour initialiser les composants et les actions
    private void initialize() {
        updateInstructionLabel(); // Met à jour l'instruction pour le joueur actuel

        // Définition de l'action du bouton "Jouer"
        view.getPlayButton().setOnAction(e -> handlePlayButton());

        // Ajout d'écouteurs pour chaque case à cocher (positions)
        for (CheckBox checkBox : view.getCheckBoxes()) {
            checkBox.setOnAction(e -> handleCheckBoxSelection(checkBox));
        }
    }

    // Méthode pour mettre à jour le label d'instruction pour le joueur actuel
    private void updateInstructionLabel() {
        String currentPlayerName = model.getCurrentPlayerName();
        if (currentPlayerName != null) {
            view.getInstructionLabel().setText(currentPlayerName + ", veuillez choisir votre emplacement");
        }
    }

    // Méthode appelée lorsque le bouton "Jouer" est cliqué
    private void handlePlayButton() {
        int selectedPosition = getSelectedPosition(); // Obtient la position sélectionnée
        if (selectedPosition != -1) {
            model.addChosenPosition(selectedPosition); // Ajoute la position choisie au modèle
            takenPositions.add(selectedPosition);      // Marque la position comme prise

            // Désactive la case à cocher correspondante
            CheckBox selectedCheckBox = getCheckboxByPosition(selectedPosition);
            if (selectedCheckBox != null) {
                selectedCheckBox.setDisable(true);
                selectedCheckBox.setSelected(true);
            }

            model.advanceToNextPlayer(); // Passe au joueur suivant

            if (model.hasNextPlayer()) {
                // Si d'autres joueurs doivent encore choisir, on met à jour l'interface
                updateInstructionLabel();
                view.getPlayButton().setDisable(true);
                enableAvailableCheckBoxes();
            } else {
                // Si tous les joueurs ont choisi, on affiche les résultats
                model.sortChosenPlayersPositions();
                model.updateAcceptedPlayersWithPositions();
                showResults();
                // Envoyer le lancement de la partie (à implémenter si nécessaire)
            }
        }
    }

    // Méthode appelée lorsqu'une case à cocher est sélectionnée
    private void handleCheckBoxSelection(CheckBox selectedCheckBox) {
        if (selectedCheckBox.isSelected()) {
            view.getPlayButton().setDisable(false);    // Active le bouton "Jouer"
            disableOtherCheckBoxes(selectedCheckBox);  // Désactive les autres cases non sélectionnées
        } else {
            view.getPlayButton().setDisable(true);     // Désactive le bouton "Jouer"
            enableAvailableCheckBoxes();               // Réactive les cases disponibles
        }
    }

    // Désactive les autres cases à cocher non sélectionnées
    private void disableOtherCheckBoxes(CheckBox selectedCheckBox) {
        for (CheckBox checkBox : view.getCheckBoxes()) {
            if (checkBox != selectedCheckBox && !takenPositions.contains(getPositionFromCheckbox(checkBox))) {
                checkBox.setDisable(true);
            }
        }
    }

    // Réactive les cases à cocher disponibles
    private void enableAvailableCheckBoxes() {
        for (CheckBox checkBox : view.getCheckBoxes()) {
            int position = getPositionFromCheckbox(checkBox);
            if (!takenPositions.contains(position)) {
                checkBox.setDisable(false);
                checkBox.setSelected(false);
            } else {
                checkBox.setDisable(true);
                checkBox.setSelected(true);
            }
        }
    }

    // Obtient la position sélectionnée par le joueur
    private int getSelectedPosition() {
        for (CheckBox checkBox : view.getCheckBoxes()) {
            if (checkBox.isSelected() && !takenPositions.contains(getPositionFromCheckbox(checkBox))) {
                return getPositionFromCheckbox(checkBox);
            }
        }
        return -1;
    }

    // Récupère la position associée à une case à cocher
    private int getPositionFromCheckbox(CheckBox checkBox) {
        return (int) checkBox.getUserData();
    }

    // Récupère la case à cocher correspondant à une position donnée
    private CheckBox getCheckboxByPosition(int position) {
        for (CheckBox checkBox : view.getCheckBoxes()) {
            if ((int) checkBox.getUserData() == position) {
                return checkBox;
            }
        }
        return null;
    }

    // Affiche les résultats une fois que tous les joueurs ont choisi leurs positions
    private void showResults() {
        VBox resultsLayout = new VBox(10);
        resultsLayout.setAlignment(Pos.CENTER);
        resultsLayout.getChildren().add(new Label("Début de partie !"));
        resultsLayout.getChildren().add(new Label("Liste des joueurs dans l'ordre de jeu :"));
        
        String listej = "";

        // Construit la liste des identifiants des joueurs
        for (Pair<String, Integer> choice : model.getChosenPlayersPositions()) {
            Player player = model.getAcceptedPlayers().get(choice.getKey());
            if (player != null) {
                listej += player.getId() + ",";
            }
        }
        
        // Retire la dernière virgule de la liste
        listej = listej.substring(0, listej.length() - 1);
        
        // Affiche les informations des joueurs et envoie un message à chacun
        for (Pair<String, Integer> choice : model.getChosenPlayersPositions()) {
            String playerName = choice.getKey();
            int position = choice.getValue();
            Player player = model.getAcceptedPlayers().get(playerName);
            if (player != null) {
                String playerInfo = String.format("Nom: %s, IP: %s, ID: %s, Position: %d",
                        player.getName(), player.getIp(), player.getId(), position);
                resultsLayout.getChildren().add(new Label(playerInfo));
                
                // Prépare et envoie le message d'initialisation de partie (IP) à chaque joueur
                LinkedHashMap<String, String> attributs = new LinkedHashMap<>();
                attributs.put("listej", listej);
                attributs.put("idp", partiemodel.getGameAttributes().get("idp"));
                
                String message = Data.writeMessage("IP", attributs);
                System.out.println(message);
                Data.tcpSender(player.getIp(), 7777, message);
            }
        }

        // Applique la rotation à la vue des résultats si nécessaire
        resultsLayout.setRotate(view.getLayout().getRotate());
        Platform.runLater(() -> primaryStage.getScene().setRoot(resultsLayout));
        List<Player> players = new ArrayList<Player>();
        for (Pair<String, Integer> choice : model.getChosenPlayersPositions()) {
            Player player = model.getAcceptedPlayers().get(choice.getKey());
            if (player != null) {
                players.add(player);
            }
        }
        
        GameInterfacePptiView gameInterface = new GameInterfacePptiView(players);
        Platform.runLater(() -> primaryStage.getScene().setRoot(gameInterface.getView()));
    }
}
