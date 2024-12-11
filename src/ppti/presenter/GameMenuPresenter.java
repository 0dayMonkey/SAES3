package ppti.presenter;

import javafx.stage.Stage;
import ppti.view.GameConfigView;
import ppti.view.GameMenuView;
import ppti.view.GameRulesView;
import ppti.view.GameSettingsView;
import ppti.model.GameConfigModel;
import ppti.model.GameMenuModel;
import ppti.model.GameRulesModel;

// Présentateur pour le menu du jeu
public class GameMenuPresenter {

    private GameMenuView view;   // La vue associée au menu du jeu
    private GameMenuModel model; // Le modèle associé au menu du jeu

    // Constructeur du présentateur
    public GameMenuPresenter(GameMenuView view, GameMenuModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;

        // Gestion des actions des boutons du menu principal
        view.getNewGameButton().setOnAction(e -> startNewGame(primaryStage));    // Démarrer une nouvelle partie
        view.getSettingsButton().setOnAction(e -> openSettings(primaryStage));   // Ouvrir les paramètres
        view.getRulesButton().setOnAction(e -> openRules(primaryStage));         // Afficher les règles du jeu
        view.getQuitButton().setOnAction(e -> System.exit(0));                   // Quitter l'application

        // Gestion de l'alignement de l'interface
        view.getAlignTopButton().setOnAction(e -> rotateInterface(180));  // Aligner en haut (rotation de 180 degrés)
        view.getAlignBottomButton().setOnAction(e -> rotateInterface(0)); // Aligner en bas (rotation de 0 degré)
    }

    // Méthode pour faire pivoter l'interface selon un angle donné
    private void rotateInterface(int angle) {
        model.setCurrentAngle(angle); // Met à jour l'angle actuel dans le modèle
        view.rotateInterface(angle);  // Applique la rotation à la vue
    }

    // Méthode pour démarrer une nouvelle partie
    @SuppressWarnings("unused")
	private void startNewGame(Stage primaryStage) {
        // Création de la vue et du modèle de configuration du jeu
        GameConfigView gameConfigView = new GameConfigView(model.getCurrentAngle());
        GameConfigModel gameConfigModel = new GameConfigModel(model.getCurrentAngle());
        // Création du présentateur pour la configuration du jeu
        GameConfigPresenter gameConfigPresenter = new GameConfigPresenter(gameConfigView, primaryStage, gameConfigModel, this.view);
        // Mise à jour de la scène principale avec la nouvelle vue
        primaryStage.getScene().setRoot(gameConfigView.getView());
    }

    // Méthode pour ouvrir les paramètres du jeu
    @SuppressWarnings("unused")
	private void openSettings(Stage primaryStage) {
        // Création de la vue des paramètres
        GameSettingsView gameSettingsView = new GameSettingsView(model.getCurrentAngle());
        // Création du présentateur pour les paramètres
        GameSettingsPresenter gameSettingsPresenter = new GameSettingsPresenter(gameSettingsView, primaryStage, this.view);
        // Mise à jour de la scène principale avec la vue des paramètres
        primaryStage.getScene().setRoot(gameSettingsView.getView());
    }

    // Méthode pour afficher les règles du jeu
    @SuppressWarnings("unused")
	private void openRules(Stage primaryStage) {
        // Création de la vue et du modèle des règles du jeu
        GameRulesView gameRulesView = new GameRulesView(model.getCurrentAngle());
        GameRulesModel gameRulesModel = new GameRulesModel();
        // Création du présentateur pour les règles du jeu
        GameRulesPresenter gameRulesPresenter = new GameRulesPresenter(gameRulesView, primaryStage, gameRulesModel, this.view);
        // Mise à jour de la scène principale avec la vue des règles
        primaryStage.getScene().setRoot(gameRulesView.getView());
    }
}
