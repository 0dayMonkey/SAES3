package ppti.presenter;

import javafx.stage.Stage;
import ppti.view.GameMenuView;
import ppti.view.GameSettingsView;

// Présentateur pour la vue des paramètres du jeu
public class GameSettingsPresenter {

    private GameSettingsView view;   // La vue des paramètres du jeu
    private Stage stage;             // La scène principale de l'application
    private GameMenuView menuView;   // La vue du menu principal du jeu

    // Constructeur du présentateur
    public GameSettingsPresenter(GameSettingsView view, Stage stage, GameMenuView menuView) {
        this.view = view;
        this.stage = stage;
        this.menuView = menuView;
        this.view.setPresenter(this); // Définit le présentateur dans la vue
    }

    // Méthode appelée lorsque le bouton "Retour" est cliqué
    public void onReturnButtonClicked() {
        // Remplace la scène actuelle par la vue du menu principal
        stage.getScene().setRoot(menuView.getView());
    }
}
