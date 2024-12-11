package ppti.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// Classe représentant la vue de la liste des joueurs
public class PlayerListView {
    private BorderPane layout;                  // Disposition principale de la vue
    private VBox pendingPlayerList;             // Liste des joueurs en attente
    private VBox acceptedPlayerList;            // Liste des joueurs acceptés
    private Label playerCountLabel;             // Label affichant le nombre de joueurs
    private ComboBox<String> playerComboBox;    // ComboBox pour sélectionner le premier joueur
    private Button backButton;                  // Bouton "Retour"
    private Button startGameButton;             // Bouton "Lancer"
    private Label titleLabel;                   // Titre de la vue

    // Constructeur qui initialise la vue avec un angle de rotation
    public PlayerListView(int angle) {
        createView(angle); // Appel à la méthode pour créer la vue
    }

    // Méthode pour créer et configurer la vue
    private void createView(int angle) {
        layout = new BorderPane();

        // Titre du jeu
        titleLabel = new Label("Un mouton à la mer");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-family: 'Comic Sans MS';");

        // Initialisation des listes des joueurs
        pendingPlayerList = new VBox(10);           // VBox avec un espacement de 10 pixels
        pendingPlayerList.setAlignment(Pos.CENTER); // Centrage horizontal des éléments

        acceptedPlayerList = new VBox(10);
        acceptedPlayerList.setAlignment(Pos.CENTER);

        // Label pour afficher le nombre de joueurs
        playerCountLabel = new Label();

        // ComboBox pour sélectionner le premier joueur
        playerComboBox = new ComboBox<>();

        // Boutons "Retour" et "Lancer"
        backButton = new Button("Retour");
        startGameButton = new Button("Lancer");

        // Mise en page principale
        VBox mainLayout = new VBox(20,
            titleLabel,
            acceptedPlayerList,
            pendingPlayerList,
            new Label("Définir le premier joueur :"),
            playerComboBox,
            playerCountLabel
        );
        mainLayout.setAlignment(Pos.CENTER);   // Centrage vertical des éléments
        mainLayout.setPadding(new Insets(20)); // Ajout de marges internes de 20 pixels

        // Mise en page pour les boutons en bas
        BorderPane bottomButtons = new BorderPane();
        bottomButtons.setLeft(backButton);                // Bouton "Retour" à gauche
        bottomButtons.setRight(startGameButton);          // Bouton "Lancer" à droite
        BorderPane.setMargin(backButton, new Insets(20)); // Marge autour du bouton "Retour"
        BorderPane.setMargin(startGameButton, new Insets(20)); // Marge autour du bouton "Lancer"

        // Assemblage de la vue
        layout.setCenter(mainLayout);    // Placement du contenu principal au centre
        layout.setBottom(bottomButtons); // Placement des boutons en bas
        layout.setRotate(angle);         // Application de la rotation spécifiée
    }

    // Méthode pour obtenir le layout principal
    public BorderPane getLayout() {
        return layout;
    }

    // Getters pour accéder aux composants de la vue
    public VBox getPendingPlayerList() {
        return pendingPlayerList;
    }

    public VBox getAcceptedPlayerList() {
        return acceptedPlayerList;
    }

    public Label getPlayerCountLabel() {
        return playerCountLabel;
    }

    public ComboBox<String> getPlayerComboBox() {
        return playerComboBox;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getStartGameButton() {
        return startGameButton;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    // Méthode pour obtenir le joueur sélectionné dans la ComboBox
    public String getSelectedPlayer() {
        return playerComboBox.getSelectionModel().getSelectedItem();
    }
}
