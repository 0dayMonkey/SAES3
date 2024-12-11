package ppti.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ppti.presenter.GameSettingsPresenter;

// Classe représentant la vue des paramètres du jeu
public class GameSettingsView {

    private BorderPane layout;          // Disposition principale de la vue
    private Button btnReturn;           // Bouton pour revenir au menu principal
    private ToggleGroup languagesGroup; // Groupe de boutons radio pour les langues

    // Constructeur de la vue des paramètres, avec un paramètre pour la rotation
    public GameSettingsView(int rotate) {
        layout = new BorderPane();

        // Création du bouton de retour
        btnReturn = new Button("Retour");
        btnReturn.setAlignment(Pos.CENTER_LEFT); // Alignement du bouton à gauche
        layout.setBottom(btnReturn);             // Placement du bouton en bas du layout
        BorderPane.setMargin(btnReturn, new Insets(20)); // Marge autour du bouton

        // Titre de la page
        Label title = new Label("Réglages");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;"); // Style du titre
        title.setAlignment(Pos.CENTER);

        // Label pour la section Langues
        Label languagesLabel = new Label("Langues :");
        languagesLabel.setStyle("-fx-font-size: 18px;");
        languagesLabel.setAlignment(Pos.CENTER);

        // Création des boutons radio pour les différentes langues
        RadioButton rbEnglish = new RadioButton("Anglais");
        RadioButton rbFrench = new RadioButton("Français");
        RadioButton rbSpanish = new RadioButton("Espagnol");
        RadioButton rbGerman = new RadioButton("Allemand");
        RadioButton rbRussian = new RadioButton("Russe");
        RadioButton rbDutch = new RadioButton("Néerlandais");
        RadioButton rbItalian = new RadioButton("Italien");
        RadioButton rbChinese = new RadioButton("Chinois");
        RadioButton rbJapanese = new RadioButton("Japonais");
        RadioButton rbSwedish = new RadioButton("Suédois");
        RadioButton rbPolish = new RadioButton("Polonais");
        RadioButton rbTurkish = new RadioButton("Turc");
        RadioButton rbArabic = new RadioButton("Arabe");
        RadioButton rbNorwegian = new RadioButton("Norvégien");
        RadioButton rbPortuguese = new RadioButton("Portugais");

        // Ajout des boutons radio dans un groupe pour permettre une seule sélection
        ToggleGroup languagesGroup = new ToggleGroup();
        rbEnglish.setToggleGroup(languagesGroup);
        rbFrench.setToggleGroup(languagesGroup);
        rbSpanish.setToggleGroup(languagesGroup);
        rbGerman.setToggleGroup(languagesGroup);
        rbRussian.setToggleGroup(languagesGroup);
        rbDutch.setToggleGroup(languagesGroup);
        rbItalian.setToggleGroup(languagesGroup);
        rbChinese.setToggleGroup(languagesGroup);
        rbJapanese.setToggleGroup(languagesGroup);
        rbSwedish.setToggleGroup(languagesGroup);
        rbPolish.setToggleGroup(languagesGroup);
        rbTurkish.setToggleGroup(languagesGroup);
        rbArabic.setToggleGroup(languagesGroup);
        rbNorwegian.setToggleGroup(languagesGroup);
        rbPortuguese.setToggleGroup(languagesGroup);
        rbFrench.setSelected(true); // Sélection par défaut sur le français

        // Création d'une grille pour disposer les boutons radio
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);              // Espacement horizontal entre les cellules
        gridPane.setVgap(10);              // Espacement vertical entre les cellules
        gridPane.setAlignment(Pos.CENTER); // Centrer la grille

        // Ajout des langues dans la grille (3 lignes et 5 colonnes)
        gridPane.add(rbEnglish, 0, 0);
        gridPane.add(rbFrench, 1, 0);
        gridPane.add(rbSpanish, 2, 0);
        gridPane.add(rbGerman, 3, 0);
        gridPane.add(rbRussian, 4, 0);
        gridPane.add(rbDutch, 0, 1);
        gridPane.add(rbItalian, 1, 1);
        gridPane.add(rbChinese, 2, 1);
        gridPane.add(rbJapanese, 3, 1);
        gridPane.add(rbSwedish, 4, 1);
        gridPane.add(rbPolish, 0, 2);
        gridPane.add(rbTurkish, 1, 2);
        gridPane.add(rbArabic, 2, 2);
        gridPane.add(rbNorwegian, 3, 2);
        gridPane.add(rbPortuguese, 4, 2);

        // Création d'un VBox pour disposer verticalement le titre, le label et la grille
        VBox vbox = new VBox(25, title, languagesLabel, gridPane);
        vbox.setAlignment(Pos.CENTER); // Centrer le contenu du VBox
        layout.setCenter(vbox);        // Placer le VBox au centre du layout
        rotate(rotate);                // Appliquer la rotation si nécessaire
    }

    // Méthode pour définir le présentateur et lier l'action du bouton "Retour"
    public void setPresenter(GameSettingsPresenter presenter) {
        btnReturn.setOnAction(e -> presenter.onReturnButtonClicked());
    }

    // Méthode pour obtenir le layout principal de la vue
    public BorderPane getView() {
        return layout;
    }

    // Méthode pour obtenir le groupe de langues sélectionnées
    public ToggleGroup getLanguagesGroup() {
        return languagesGroup;
    }

    // Méthode pour appliquer une rotation au layout
    public void rotate(int angle) {
        layout.setRotate(angle);
    }
}
