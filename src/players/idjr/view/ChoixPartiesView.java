package players.idjr.view;

import players.idjr.model.Partie;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ChoixPartiesView {
    private BorderPane rootChoixPartiesView;
    private VBox partiesList;
    private Button retourButton;

    public ChoixPartiesView() {
        creerVueChoixParties();
    }

    private void creerVueChoixParties() {
        rootChoixPartiesView = new BorderPane();

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);

        Label titre = new Label("Liste des parties");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        partiesList = new VBox(10);
        partiesList.setPadding(new Insets(20, 0, 50, 0));
        partiesList.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(partiesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        mainContainer.getChildren().addAll(titre, scrollPane);
        rootChoixPartiesView.setCenter(mainContainer);

        retourButton = new Button("Retour");
        retourButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 14px;");
        rootChoixPartiesView.setTop(retourButton);
    }

    public BorderPane getChoixPartiesView() {
        return rootChoixPartiesView;
    }

    public void setRetourAction(Runnable action) {
        retourButton.setOnAction(e -> action.run());
    }

    // Ajouter une partie à la vue
    public void ajouterPartie(Partie partie, Runnable rejoindreAction) {
        HBox partieBox = new HBox(100);
        partieBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;");
        partieBox.setMaxWidth(550);

        Label nomPartieLabel = new Label(partie.getNomPartie());
        nomPartieLabel.setStyle("-fx-font-size: 16px;");

        Button rejoindreButton = new Button("Rejoindre");
        rejoindreButton.setStyle("-fx-pref-width: 100px; -fx-pref-height: 40px; " +
                                 "-fx-background-color: white; -fx-text-fill: black; " +
                                 "-fx-border-color: black; -fx-border-width: 0.5px;");

        HBox.setHgrow(nomPartieLabel, javafx.scene.layout.Priority.ALWAYS);
        nomPartieLabel.setMaxWidth(Double.MAX_VALUE);

        HBox.setMargin(nomPartieLabel, new Insets(5, 10, 5, 10));
        HBox.setMargin(rejoindreButton, new Insets(5, 10, 5, 10));

        partieBox.setAlignment(Pos.CENTER_LEFT);

        partieBox.getChildren().addAll(nomPartieLabel, rejoindreButton);

        rejoindreButton.setOnAction(e -> rejoindreAction.run());

        partiesList.getChildren().add(partieBox);
    }

    // Modifier une partie dans la vue (par exemple, pour mettre à jour le statut)
    public void modifierPartie(String nomPartie, Label statutPartie) {
    	 // Parcourir la liste des parties pour trouver la HBox correspondante au nom de la partie
        for (Node node : partiesList.getChildren()) {
            if (node instanceof HBox) {
                HBox partieBox = (HBox) node;
                // Rechercher le Label contenant le nom de la partie
                Label nomPartieLabel = (Label) partieBox.getChildren().get(0);
                if (nomPartieLabel.getText().equals(nomPartie)) {
                    // Insérer le nouveau label (statut) à la position désirée
                    if (partieBox.getChildren().size() == 2) {
                        partieBox.getChildren().add(1, statutPartie); // Ajouter le label de statut entre le nom et le bouton
                    } else {
                        partieBox.getChildren().set(1, statutPartie); // Modifier le statut s'il existe déjà
                    }
                    HBox.setMargin(statutPartie, new Insets(5, 10, 5, 10));
                    break; // Sortir de la boucle une fois la partie trouvée et modifiée
                }
            }
        }
    }

    // Supprimer une partie de la vue
    public void supprimerPartie(String nomPartie) {
        partiesList.getChildren().removeIf(node -> {
            if (node instanceof HBox) {
                HBox partieBox = (HBox) node;
                Label nomPartieLabel = (Label) partieBox.getChildren().get(0);
                return nomPartieLabel.getText().equals(nomPartie);
            }
            return false;
        });
    }

    public int getNombreDeParties() {
        return partiesList.getChildren().size();
    }

    public void disableRejoindreButton(int index) {
        if (index >= 0 && index < partiesList.getChildren().size()) {
            HBox partieBox = (HBox) partiesList.getChildren().get(index);
            Button rejoindreButton = (Button) partieBox.getChildren().get(1);
            rejoindreButton.setDisable(true);
            rejoindreButton.setText("Terminé");
        }
    }

    public int getIndexOfParty(String nomPartie) {
        for (int i = 0; i < partiesList.getChildren().size(); i++) {
            Node node = partiesList.getChildren().get(i);
            if (node instanceof HBox) {
                HBox partieBox = (HBox) node;
                Label nomPartieLabel = (Label) partieBox.getChildren().get(0);
                if (nomPartieLabel.getText().equals(nomPartie)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void clearParties() {
        partiesList.getChildren().clear();
    }
}
