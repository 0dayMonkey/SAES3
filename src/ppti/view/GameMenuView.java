package ppti.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// Classe représentant la vue du menu du jeu
public class GameMenuView {

    // Composants de l'interface utilisateur
    private VBox vbox;
    private Label title;
    private Button newGameButton;
    private Button settingsButton;
    private Button rulesButton;
    private Button quitButton;
    private Button alignTop;
    private Button alignBottom;
    private BorderPane root;

    // Constructeur de la vue du menu du jeu
    public GameMenuView() {
        // Initialisation des composants de la vue
        title = new Label("Un mouton à la mer");          // Titre du jeu
        newGameButton = new Button("Nouvelle partie");    // Bouton pour démarrer une nouvelle partie
        settingsButton = new Button("Réglages");          // Bouton pour accéder aux réglages
        rulesButton = new Button("Règles");               // Bouton pour afficher les règles du jeu
        quitButton = new Button("Quitter le jeu");        // Bouton pour quitter le jeu

        // Création d'un VBox pour disposer les éléments verticalement avec un espacement de 15 pixels
        vbox = new VBox(15, title, newGameButton, rulesButton, settingsButton, quitButton);
        vbox.setAlignment(Pos.CENTER); // Centrer les éléments dans le VBox

        // Boutons pour aligner l'interface
        alignTop = new Button("Aligner");
        alignTop.setRotate(180); // Rotation de 180 degrés pour le bouton du haut
        alignBottom = new Button("Aligner"); // Bouton du bas sans rotation

        // Création du conteneur principal
        root = new BorderPane();
        root.setCenter(vbox); // Placement du VBox au centre du BorderPane

        // Configuration de l'alignement et des marges pour les boutons d'alignement
        BorderPane.setAlignment(alignTop, Pos.TOP_CENTER);
        BorderPane.setAlignment(alignBottom, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(alignTop, new Insets(20, 0, 0, 0));     // Marge supérieure pour le bouton du haut
        BorderPane.setMargin(alignBottom, new Insets(0, 0, 20, 0));  // Marge inférieure pour le bouton du bas

        // Ajout des boutons d'alignement au BorderPane
        root.setTop(alignTop);
        root.setBottom(alignBottom);
    }

    // Méthode pour obtenir la vue principale
    public BorderPane getView() {
        return root;
    }

    // Getters pour les composants de l'interface utilisateur
    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getAlignTopButton() {
        return alignTop;
    }

    public Button getAlignBottomButton() {
        return alignBottom;
    }

    public VBox getVbox() {
        return vbox;
    }

    public Label getTitle() {
        return title;
    }

    // Méthode pour faire pivoter l'interface selon un angle donné
    public void rotateInterface(int angle) {
        vbox.setRotate(angle);
    }

    // Méthode pour redimensionner les composants en fonction d'un facteur d'échelle
    public void resizeComponents(double scaleFactor) {
        // Ajustement de la taille et du style du titre
        title.setStyle("-fx-font-size: " + (36 * scaleFactor) + "px; -fx-font-family: 'Comic Sans MS';");
        // Ajustement de la taille des boutons
        newGameButton.setStyle("-fx-font-size: " + (16 * scaleFactor) + "px;");
        settingsButton.setStyle("-fx-font-size: " + (16 * scaleFactor) + "px;");
        rulesButton.setStyle("-fx-font-size: " + (16 * scaleFactor) + "px;");
        quitButton.setStyle("-fx-font-size: " + (16 * scaleFactor) + "px;");
        // Ajustement de la largeur préférée des boutons
        newGameButton.setPrefWidth(150 * scaleFactor);
        quitButton.setPrefWidth(150 * scaleFactor);
    }
}
