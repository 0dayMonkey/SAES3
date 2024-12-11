package ppti.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

// Classe représentant la vue pour la sélection des positions des joueurs
public class PlayerPositionView {
    private BorderPane layout;                   // Disposition principale de la vue
    private List<CheckBox> checkBoxes;           // Liste des cases à cocher pour les positions
    private Label instructionLabel;              // Label pour afficher les instructions aux joueurs
    private Button playButton;                   // Bouton pour valider le choix
    private int rotate;                          // Angle de rotation de la vue

    // Tableau des positions ajustées avec des positions échangées
    private final int[] positions = {1, 2, 3, 4, 5, 8, 7, 6, 10, 9};
    private int positionIndex = 0;               // Indice courant dans le tableau des positions

    // Constructeur de la vue, prenant en paramètre l'angle de rotation
    public PlayerPositionView(int rotate) {
        this.rotate = rotate;
        this.checkBoxes = new ArrayList<>();
        createView();                            // Appel à la méthode pour créer la vue
    }

    // Méthode pour créer et configurer la vue
    private void createView() {
        // Création du label pour les instructions
        instructionLabel = new Label();
        instructionLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Comic Sans MS';");

        // Bouton "Valider Choix"
        playButton = new Button("Valider Choix");
        playButton.setDisable(true);             // Désactiver le bouton par défaut
        playButton.setStyle("-fx-font-size: 18px; -fx-font-family: 'Comic Sans MS';");

        // Disposition principale de la vue
        layout = new BorderPane();
        layout.setTop(createHBoxWithCheckboxes(3));     // Ligne supérieure avec 3 cases à cocher
        layout.setRight(createVBoxWithCheckboxes(2));   // Colonne de droite avec 2 cases à cocher
        layout.setBottom(createHBoxWithCheckboxes(3));  // Ligne inférieure avec 3 cases à cocher
        layout.setLeft(createVBoxWithCheckboxes(2));    // Colonne de gauche avec 2 cases à cocher
        layout.setRotate(rotate);                       // Appliquer la rotation à la vue

        // Conteneur central avec le label d'instruction et le bouton "Valider Choix"
        VBox centerContainer = new VBox(10);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.getChildren().addAll(instructionLabel, playButton);
        layout.setCenter(centerContainer);              // Ajouter le conteneur central au layout
    }

    // Getter pour obtenir le layout principal
    public BorderPane getLayout() {
        return layout;
    }

    // Getter pour le label d'instruction
    public Label getInstructionLabel() {
        return instructionLabel;
    }

    // Getter pour le bouton "Valider Choix"
    public Button getPlayButton() {
        return playButton;
    }

    // Getter pour la liste des cases à cocher
    public List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    // Méthode pour créer un HBox contenant un certain nombre de cases à cocher
    private HBox createHBoxWithCheckboxes(int numCheckBoxes) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20));

        for (int i = 0; i < numCheckBoxes; i++) {
            CheckBox checkBox = new CheckBox();
            int position = positions[positionIndex];
            checkBox.setUserData(position);             // Associer la position à la case à cocher
            positionIndex++;
            hbox.getChildren().addAll(createSpacer(), checkBox);  // Ajouter un espace et la case à cocher
            checkBoxes.add(checkBox);                    // Ajouter la case à cocher à la liste
        }
        hbox.getChildren().add(createSpacer());          // Ajouter un espace à la fin
        return hbox;
    }

    // Méthode pour créer un VBox contenant un certain nombre de cases à cocher
    private VBox createVBoxWithCheckboxes(int numCheckBoxes) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        for (int i = 0; i < numCheckBoxes; i++) {
            CheckBox checkBox = new CheckBox();
            int position = positions[positionIndex];
            checkBox.setUserData(position);              // Associer la position à la case à cocher
            positionIndex++;
            vbox.getChildren().addAll(createSpacer(), checkBox);  // Ajouter un espace et la case à cocher
            checkBoxes.add(checkBox);                     // Ajouter la case à cocher à la liste
        }
        vbox.getChildren().add(createSpacer());           // Ajouter un espace à la fin
        return vbox;
    }

    // Méthode pour créer un espaceur flexible
    private Region createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);  // Permet à l'espaceur de grandir horizontalement
        VBox.setVgrow(spacer, Priority.ALWAYS);  // Permet à l'espaceur de grandir verticalement
        return spacer;
    }
}
