package ppti.presenter;

import java.util.LinkedHashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import ppti.data.Data;
import ppti.model.GameConfigModel;
import ppti.model.PlayerListModel;
import ppti.view.GameConfigView;
import ppti.view.GameMenuView;
import ppti.view.PlayerListView;

// Présentateur pour la configuration du jeu
public class GameConfigPresenter {
    private GameConfigModel model;      // Le modèle de configuration du jeu
    private GameConfigView view;        // La vue de configuration du jeu
    private Stage stage;                // La scène principale
    private GameMenuView menuview;      // La vue du menu du jeu

    // Constructeur du présentateur
    public GameConfigPresenter(GameConfigView view, Stage stage, GameConfigModel model, GameMenuView menuview) {
        this.model = model;
        this.stage = stage;
        this.view = view;
        this.menuview = menuview;

        initialize(); // Initialisation des composants et des écouteurs
    }

    // Méthode pour initialiser les écouteurs et les actions
    @SuppressWarnings("unused")
	private void initialize() {
        // Écouteur pour le champ de saisie du nom de la partie
        view.setPartNameInputListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean isEmpty = newValue.trim().isEmpty();
                view.setStartGameButtonDisabled(isEmpty); // Désactiver le bouton si le nom est vide
            }
        });
        
        // Écouteurs pour les Spinners (sélecteurs) des nombres de joueurs et de bots
        view.setPlayerCountSpinnerListener((observable, oldValue, newValue) -> {
            updateBotSpinnersMaxValues(); // Mettre à jour les valeurs max des spinners des bots
        });

        view.setBotASpinnerListener((observable, oldValue, newValue) -> {
            updateBotSpinnersMaxValues();
        });

        view.setBotBSpinnerListener((observable, oldValue, newValue) -> {
            updateBotSpinnersMaxValues();
        });

        view.setBotCSpinnerListener((observable, oldValue, newValue) -> {
            updateBotSpinnersMaxValues();
        });
        
        // Action pour le bouton "Retour"
        view.setBackButtonAction(() -> {
            stage.getScene().setRoot(menuview.getView()); // Retour au menu principal
        });

        // Action pour le bouton "Démarrer la partie"
        view.setStartGameButtonAction(() -> {
            // Récupérer les données saisies dans la vue
            model.setPartName(view.getPartName());
            model.setPlayerCount(view.getPlayerCount());
            model.setBotA(view.getBotA());
            model.setBotB(view.getBotB());
            model.setBotC(view.getBotC());
            
            // Préparer et envoyer un message via UDP
            LinkedHashMap<String, String> attributes = model.getAttributes();
            String message = Data.writeMessage("ACP", attributes);
            String localInterface = Data.interfaceFinder();
            Data.udpEmitter(message, localInterface);
            
            // Préparer les données pour la liste des joueurs
            LinkedHashMap<String, String> botcount = new LinkedHashMap<String, String>();
            botcount.put("BOTF", "" + model.getBotA());
            botcount.put("BOTM", "" + model.getBotB());
            botcount.put("BOTE", "" + model.getBotC());
            
            // Créer le modèle, la vue et le présentateur pour la liste des joueurs
            PlayerListModel playerListmodel = new PlayerListModel(attributes, botcount);
            PlayerListView playerListview = new PlayerListView(model.getCurrentAngle());
            PlayerListPresenter playerListpresenter = new PlayerListPresenter(playerListview, playerListmodel, stage, model.getCurrentAngle(), view);

            // Mettre à jour la scène avec la nouvelle vue
            stage.getScene().setRoot(playerListview.getLayout());
        });

        // Désactiver le bouton "Démarrer la partie" si le nom de la partie est vide
        view.setStartGameButtonDisabled(view.getPartName().trim().isEmpty());
    }
    
    // Méthode pour mettre à jour les valeurs maximales des spinners des bots
    private void updateBotSpinnersMaxValues() {
        int playerCount = view.getPlayerCount();  // Nombre total de joueurs
        int botATotal = view.getBotA();           // Nombre de bots de type A
        int botBTotal = view.getBotB();           // Nombre de bots de type B
        int botCTotal = view.getBotC();           // Nombre de bots de type C

        // On ajuste les valeurs maximales des spinners des bots pour ne pas dépasser le nombre de joueurs
        int maxBotA = playerCount - botBTotal - botCTotal;
        int maxBotB = playerCount - botATotal - botCTotal;
        int maxBotC = playerCount - botATotal - botBTotal;

        // S'assurer que les valeurs maximales ne sont pas négatives
        if (maxBotA < 0) maxBotA = 0;
        if (maxBotB < 0) maxBotB = 0;
        if (maxBotC < 0) maxBotC = 0;

        // Mettre à jour les valeurs maximales dans la vue
        view.setBotAMaxValue(maxBotA);
        view.setBotBMaxValue(maxBotB);
        view.setBotCMaxValue(maxBotC);
    }
}
