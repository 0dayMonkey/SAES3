package players.idjr.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class MenuPrincipalView {
    private BorderPane root;
    private Button jouerButton;
    private Button quitterButton;
    private Button parametresButton;

    public MenuPrincipalView() {
        creerVue();
    }

    private void creerVue() {
        root = new BorderPane();
        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);

        Label titre = new Label("Un mouton à la mer");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        jouerButton = new Button("Jouer");
        quitterButton = new Button("Quitter");

        String buttonStyle = "-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 2px; -fx-font-size: 16px; -fx-pref-width: 150px; -fx-pref-height: 40px;";
        jouerButton.setStyle(buttonStyle);
        quitterButton.setStyle(buttonStyle);

        // Création du bouton paramètres avec l'icône
        //Image parametresIcon = new Image("file:C:\\Users\\Hp\\Downloads\\reglages.png");
        parametresButton = new Button("Réglages");
        //parametresButton.setGraphic(new ImageView(parametresIcon));
        parametresButton.setStyle("-fx-background-color: transparent;");

        centerContent.getChildren().addAll(titre, jouerButton, quitterButton);
        root.setCenter(centerContent);

        // Placement du bouton paramètres en haut à droite
        StackPane topRight = new StackPane(parametresButton);
        StackPane.setAlignment(parametresButton, Pos.TOP_RIGHT);
        StackPane.setMargin(parametresButton, new Insets(10));
        root.setTop(topRight);
    }

    public BorderPane getView() {
        return root;
    }

    public Button getJouerButton() {
        return jouerButton;
    }

    public Button getQuitterButton() {
        return quitterButton;
    }

    public Button getParametresButton() {
        return parametresButton;
    }
}
