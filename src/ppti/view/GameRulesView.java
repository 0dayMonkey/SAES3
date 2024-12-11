package ppti.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import ppti.presenter.GameRulesPresenter;

// Classe représentant la vue des règles du jeu
public class GameRulesView {
    private BorderPane layout;           // Disposition principale de la vue
    private WebView rulesTextArea;       // Zone pour afficher le texte des règles sous forme de contenu web
    private Button closeButton;          // Bouton pour fermer la vue ou retourner au menu

    // Constructeur de la vue, prenant en paramètre l'angle de rotation
    public GameRulesView(int rotate) {
        layout = new BorderPane();      // Initialisation du layout principal
        rulesTextArea = new WebView();  // Initialisation de la zone web pour afficher les règles

        // Titre de la vue
        Label titleLabel = new Label("Règle du jeu");
        titleLabel.setStyle("-fx-font-size: 60px;");
        BorderPane.setMargin(titleLabel, new Insets(30)); // Ajout de marges autour du titre

        // Bouton de fermeture
        closeButton = new Button("J'ai compris");
        closeButton.setStyle("-fx-font-size: 18px;");
        BorderPane.setMargin(closeButton, new Insets(20)); // Ajout de marges autour du bouton
        // Alignement des éléments dans le BorderPane
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setAlignment(rulesTextArea, Pos.CENTER);
        BorderPane.setAlignment(closeButton, Pos.CENTER);

        // Placement des éléments dans le BorderPane
        layout.setTop(titleLabel);           // Placer le titre en haut
        layout.setCenter(rulesTextArea);     // Placer la zone de texte au centre
        layout.setBottom(closeButton);       // Placer le bouton en bas
        layout.setStyle("-fx-background-color: #FFFFFF;"); // Définir la couleur de fond en blanc

        rotate(rotate); // Appliquer la rotation à la vue si nécessaire
    }

    // Méthode pour afficher les règles du jeu dans la WebView
    public void displayRules(String rules) {
        rulesTextArea.getEngine().loadContent(rules);
    }
    
    // Méthode pour définir le présentateur et associer l'action du bouton
    public void setPresenter(GameRulesPresenter gameRulesPresenter) {
        closeButton.setOnAction(e -> gameRulesPresenter.onReturnButtonClicked());
    }

    // Méthode pour obtenir le layout principal de la vue
    public BorderPane getView() {
        return layout;
    }

    // Méthode pour appliquer une rotation à la vue
    void rotate(int rotate) {
        layout.setRotate(rotate);
    }
}
