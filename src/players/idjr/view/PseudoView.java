package players.idjr.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class PseudoView {
    private BorderPane root;
    private TextField pseudoField;
    private Button validerButton;
    private Button retourButton;
    private Label messageLabel; // Pour afficher des messages d'erreur ou d'information

    public PseudoView() {
        creerVue();
    }

    private void creerVue() {
        root = new BorderPane();
        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(20));

        Label titre = new Label("Votre pseudo");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        pseudoField = new TextField();
        pseudoField.setStyle("-fx-font-size: 16px; -fx-pref-width: 250px;");
        pseudoField.setMaxWidth(250);
        pseudoField.setPromptText("Entrez votre pseudo");

        validerButton = new Button("Valider");
        String buttonStyle = "-fx-background-color: white; -fx-text-fill: black; " +
                             "-fx-border-color: black; -fx-border-width: 2px; " +
                             "-fx-font-size: 16px; -fx-pref-width: 150px; " +
                             "-fx-pref-height: 40px;";
        validerButton.setStyle(buttonStyle);

        retourButton = new Button("Retour");
        retourButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; " +
                             "-fx-font-size: 14px; -fx-border-color: black; -fx-border-width: 2px;");

        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        centerContent.getChildren().addAll(titre, pseudoField, validerButton, messageLabel);
        root.setCenter(centerContent);

        StackPane topLeft = new StackPane(retourButton);
        StackPane.setAlignment(retourButton, Pos.TOP_LEFT);
        StackPane.setMargin(retourButton, new Insets(10));
        root.setTop(topLeft);
    }

    public BorderPane getView() {
        return root;
    }

    public void setValiderAction(Runnable action) {
        validerButton.setOnAction(e -> action.run());
    }

    public void setRetourAction(Runnable action) {
        retourButton.setOnAction(e -> action.run());
    }

    public String getPseudo() {
        return pseudoField.getText();
    }

    // Méthodes pour afficher ou effacer des messages
    public void afficherMessage(String message) {
        messageLabel.setText(message);
    }

    public void effacerMessage() {
        messageLabel.setText("");
    }
}
