package ppti.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ppti.view.GameMenuView;
import ppti.model.GameMenuModel;
import ppti.presenter.GameMenuPresenter;

// Classe principale qui étend Application pour une application JavaFX
public class Main extends Application {

    @SuppressWarnings("unused")
	@Override
    public void start(Stage primaryStage) {
        try {
            // Instanciation du modèle, de la vue et du présentateur du menu du jeu
            GameMenuView gameMenuView = new GameMenuView();
            GameMenuModel gameMenuModel = new GameMenuModel();
            GameMenuPresenter gameMenuPresenter = new GameMenuPresenter(gameMenuView, gameMenuModel, primaryStage);

            // Création de la scène avec la vue du menu du jeu, dimensions 600x400
            Scene scene = new Scene(gameMenuView.getView(), 600, 400);
            // Rotation de la racine de la scène selon l'angle actuel du modèle
            scene.getRoot().setRotate(gameMenuModel.getCurrentAngle());
            // Configuration de la scène principale
            primaryStage.setScene(scene);
            primaryStage.setTitle("Un mouton à la mer"); // Définition du titre de la fenêtre
            primaryStage.setFullScreen(true); // Mettre la fenêtre en plein écran
            primaryStage.show(); // Afficher la fenêtre
        } catch (Exception e) {
            e.printStackTrace(); // Afficher la pile d'erreurs en cas d'exception
        }
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        launch(args);
    }
}
