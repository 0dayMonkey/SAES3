package ppti.view;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// Classe représentant la vue de configuration du jeu
public class GameConfigView {
    private AnchorPane rootPane;                // Pane racine de la vue
    private VBox mainLayout;                    // Disposition verticale principale
    private Button backButton;                  // Bouton "Retour"
    private Button startGameButton;             // Bouton "Créer la partie"
    private TextField partNameInput;            // Champ de saisie pour le nom de la partie
    private Spinner<Integer> playerCountSpinner; // Sélecteur pour le nombre de joueurs
    private Spinner<Integer> botASpinner;       // Sélecteur pour le nombre de bots de type A
    private Spinner<Integer> botBSpinner;       // Sélecteur pour le nombre de bots de type B
    private Spinner<Integer> botCSpinner;       // Sélecteur pour le nombre de bots de type C

    // Constructeur de la vue avec un paramètre pour la rotation
    public GameConfigView(int rotate) {
        createView(rotate); // Initialise la vue avec la rotation spécifiée
    }

    // Méthode pour créer et configurer les éléments de la vue
    private void createView(int rotate) {
        // Titre de la scène
        Label titleLabel = new Label("Un mouton à la mer");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-family: 'Comic Sans MS';");

        // Champ de saisie pour le nom de la partie
        Label partNameLabel = new Label("Nom de la partie :");
        partNameInput = new TextField();
        partNameInput.setPrefWidth(200);

        // HBox pour disposer le label et le champ de saisie du nom de la partie
        HBox partNameLayout = new HBox(10, partNameLabel, partNameInput);
        partNameLayout.setAlignment(Pos.CENTER);

        // HBox pour le nombre de joueurs
        Label playerCountLabel = new Label("Nombre de joueurs :");
        playerCountSpinner = new Spinner<>(3, 5, 3); // Spinner de 3 à 5, valeur initiale 3

        HBox playerCountLayout = new HBox(10, playerCountLabel, playerCountSpinner);
        playerCountLayout.setAlignment(Pos.CENTER);

        // Création des spinners pour les différents types de bots
        botASpinner = new Spinner<>(0, 5, 0);
        HBox botALayout = new HBox(10, new Label("Nombre de bot A :"), botASpinner);

        botBSpinner = new Spinner<>(0, 5, 0);
        HBox botBLayout = new HBox(10, new Label("Nombre de bot B :"), botBSpinner);

        botCSpinner = new Spinner<>(0, 5, 0);
        HBox botCLayout = new HBox(10, new Label("Nombre de bot C :"), botCSpinner);

        // Alignement des HBox des bots
        botALayout.setAlignment(Pos.CENTER);
        botBLayout.setAlignment(Pos.CENTER);
        botCLayout.setAlignment(Pos.CENTER);

        // Création du bouton "Créer la partie"
        startGameButton = new Button("Créer la partie");
        startGameButton.setDisable(true); // Désactiver le bouton par défaut

        // Création d'un BorderPane pour les boutons en bas
        BorderPane bottomButtons = new BorderPane();
        backButton = new Button("Retour");
        bottomButtons.setLeft(backButton);             // Placer le bouton de retour à gauche
        bottomButtons.setRight(startGameButton);       // Placer le bouton de lancement à droite

        // Création du layout principal avec tous les éléments
        mainLayout = new VBox(20, titleLabel, partNameLayout, playerCountLayout, botALayout, botBLayout, botCLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setRotate(rotate); // Appliquer la rotation au layout principal

        // Utilisation d'un StackPane pour centrer le mainLayout
        StackPane centerPane = new StackPane(mainLayout);

        // Initialisation du rootPane
        rootPane = new AnchorPane();
        rootPane.getChildren().add(centerPane);
        // Ancrer le centerPane aux quatre bords pour le centrer
        AnchorPane.setTopAnchor(centerPane, 0.0);
        AnchorPane.setBottomAnchor(centerPane, 0.0);
        AnchorPane.setLeftAnchor(centerPane, 0.0);
        AnchorPane.setRightAnchor(centerPane, 0.0);

        // Ajout des boutons au rootPane
        rootPane.getChildren().addAll(backButton, startGameButton);
        // Positionnement des boutons en fonction de la rotation
        positionButtons(rotate);
        backButton.setRotate(rotate);
        startGameButton.setRotate(rotate);
    }

    // Méthode pour positionner les boutons "Retour" et "Créer la partie" selon l'angle de rotation
    private void positionButtons(double rotate) {
        // Efface les contraintes précédentes
        AnchorPane.clearConstraints(backButton);
        AnchorPane.clearConstraints(startGameButton);

        if (rotate == 0) {
            // Position par défaut
            AnchorPane.setBottomAnchor(startGameButton, 20.0);
            AnchorPane.setRightAnchor(startGameButton, 20.0);

            AnchorPane.setBottomAnchor(backButton, 20.0);
            AnchorPane.setLeftAnchor(backButton, 20.0);
        } else if (rotate == 90 || rotate == -270) {
            // Rotation de 90 degrés
            AnchorPane.setBottomAnchor(startGameButton, 75.0);
            AnchorPane.setLeftAnchor(startGameButton, -40.0);

            AnchorPane.setTopAnchor(backButton, 75.0);
            AnchorPane.setLeftAnchor(backButton, -40.0);
        } else if (rotate == -90 || rotate == 270) {
            // Rotation de -90 degrés
            AnchorPane.setTopAnchor(startGameButton, 75.0);
            AnchorPane.setRightAnchor(startGameButton, -40.0);

            AnchorPane.setBottomAnchor(backButton, 75.0);
            AnchorPane.setRightAnchor(backButton, -40.0);
        } else if (rotate == 180 || rotate == -180) {
            // Rotation de 180 degrés
            AnchorPane.setTopAnchor(startGameButton, 20.0);
            AnchorPane.setLeftAnchor(startGameButton, 20.0);

            AnchorPane.setTopAnchor(backButton, 20.0);
            AnchorPane.setRightAnchor(backButton, 20.0);
        } else {
            // Autres rotations
            AnchorPane.setBottomAnchor(startGameButton, 10.0);
            AnchorPane.setRightAnchor(startGameButton, 10.0);

            AnchorPane.setBottomAnchor(backButton, 10.0);
            AnchorPane.setLeftAnchor(backButton, 10.0);
        }
    }

    // Méthode pour obtenir la vue principale
    public AnchorPane getView() {
        return rootPane;
    }

    // Méthodes pour obtenir les valeurs saisies par l'utilisateur
    public String getPartName() {
        return partNameInput.getText();
    }

    public int getPlayerCount() {
        return playerCountSpinner.getValue();
    }

    public int getBotA() {
        return botASpinner.getValue();
    }

    public int getBotB() {
        return botBSpinner.getValue();
    }

    public int getBotC() {
        return botCSpinner.getValue();
    }

    // Méthode pour activer ou désactiver le bouton "Créer la partie"
    public void setStartGameButtonDisabled(boolean disabled) {
        startGameButton.setDisable(disabled);
    }

    // Méthode pour définir l'action du bouton "Retour"
    public void setBackButtonAction(Runnable action) {
        backButton.setOnAction(e -> action.run());
    }

    // Méthode pour définir l'action du bouton "Créer la partie"
    public void setStartGameButtonAction(Runnable action) {
        startGameButton.setOnAction(e -> action.run());
    }

    // Méthodes pour ajouter des écouteurs aux champs de saisie et aux spinners
    public void setPartNameInputListener(ChangeListener<String> listener) {
        partNameInput.textProperty().addListener(listener);
    }

    public void setPlayerCountSpinnerListener(ChangeListener<Integer> listener) {
        playerCountSpinner.valueProperty().addListener(listener);
    }

    public void setBotASpinnerListener(ChangeListener<Integer> listener) {
        botASpinner.valueProperty().addListener(listener);
    }

    public void setBotBSpinnerListener(ChangeListener<Integer> listener) {
        botBSpinner.valueProperty().addListener(listener);
    }

    public void setBotCSpinnerListener(ChangeListener<Integer> listener) {
        botCSpinner.valueProperty().addListener(listener);
    }

    // Méthodes pour définir la valeur maximale des spinners des bots
    public void setBotAMaxValue(int max) {
        int currentValue = botASpinner.getValue();
        botASpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max, Math.min(currentValue, max)));
    }

    public void setBotBMaxValue(int max) {
        int currentValue = botBSpinner.getValue();
        botBSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max, Math.min(currentValue, max)));
    }

    public void setBotCMaxValue(int max) {
        int currentValue = botCSpinner.getValue();
        botCSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max, Math.min(currentValue, max)));
    }
}
