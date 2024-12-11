package ppti.presenter;

import ppti.view.GameMenuView;
import ppti.view.GameRulesView;
import javafx.stage.Stage;
import ppti.model.GameRulesModel;

// Présentateur pour la vue des règles du jeu
public class GameRulesPresenter {
    private final GameRulesView view;     // La vue qui affiche les règles du jeu
    private final GameRulesModel model;   // Le modèle contenant les données des règles
    private final Stage stage;            // La scène principale de l'application
    private GameMenuView menuView;        // La vue du menu principal du jeu

    // Constructeur du présentateur
    public GameRulesPresenter(GameRulesView view, Stage stage, GameRulesModel model, GameMenuView menuView) {
        this.view = view;
        this.stage = stage;
        this.model = model;
        this.menuView = menuView;
        initialize();                // Appel à la méthode d'initialisation
        view.setPresenter(this);     // Associe ce présentateur à la vue
    }

    // Méthode pour initialiser la vue avec les règles du jeu
    private void initialize() {
        view.displayRules(model.getRulesText()); // Affiche le texte des règles dans la vue
    }
    
    // Méthode appelée lorsque le bouton "Retour" est cliqué
    public void onReturnButtonClicked() {
        stage.getScene().setRoot(menuView.getView()); // Retourne à la vue du menu principal
    }
}
