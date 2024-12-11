package players.idjr.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChoixPartiesLoadingView {
    private BorderPane root;
    private VBox loadingView;

    public ChoixPartiesLoadingView() {
        root = new BorderPane();  // Initialisation du root
        loadingView = new VBox(20);
        loadingView.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Label loadingLabel = new Label("Chargement ... ");
        ProgressIndicator progressIndicator = new ProgressIndicator(); // Indicateur circulaire
        
        loadingView.getChildren().addAll(loadingLabel, progressIndicator);
        root.setCenter(loadingView);
    }

    public Scene getLoadingView() {
        return new Scene(root, 600, 400); // Pas besoin de passer le Stage ici
    }
}
