package players.idjr.presenter;

import players.idjr.model.ChoixPartiesModel;
import players.idjr.model.PseudoModel;
import players.idjr.view.ChoixPartiesView;
import players.idjr.view.MenuPrincipalView;
import players.idjr.view.PseudoView;
import javafx.application.Platform;
import javafx.stage.Stage;
import players.data.Data;

import java.util.LinkedHashMap;

public class PseudoPresenter {
    private PseudoView view;
    private PseudoModel model;
    private Stage primaryStage;

    public PseudoPresenter(PseudoView view, PseudoModel model, Stage primaryStage) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.setValiderAction(this::handleValider);
        view.setRetourAction(this::handleRetour);
    }

    private void handleValider() {
        String pseudoInput = view.getPseudo();
        model.setPseudo(pseudoInput);

        if (model.isValidPseudo()) {
            // Naviguer vers ChoixPartiesView
            ChoixPartiesView choixPartieView = new ChoixPartiesView();
            ChoixPartiesModel partieModel = new ChoixPartiesModel();
            ChoixPartiesPresenter choixPartiePresenter = new ChoixPartiesPresenter(choixPartieView, partieModel, primaryStage, model.getPseudo());

            // Commencer à écouter les parties
            choixPartiePresenter.startListeningForGames(Data.interfaceFinder());

            // Envoyer un message UDP pour rejoindre ou créer une partie
            LinkedHashMap<String, String> attributs = new LinkedHashMap<>();
            attributs.put("identite", "JR");
            attributs.put("typep", "MIX");
            attributs.put("taillep", "5");

            String message = Data.writeMessage("RP", attributs);
            String localInterface = Data.interfaceFinder();
            Data.udpEmitter(message, localInterface);

            // Mettre à jour la vue
            Platform.runLater(() -> primaryStage.getScene().setRoot(choixPartieView.getChoixPartiesView()));
        } else {
            // Afficher un message d'erreur dans la vue
            Platform.runLater(() -> view.afficherMessage("Pseudo invalide. Veuillez réessayer."));
        }
    }

    @SuppressWarnings("unused")
	private void handleRetour() {
        // Naviguer vers le Menu Principal
        MenuPrincipalView menuPrincipalView = new MenuPrincipalView();
        MenuPrincipalPresenter menuPrincipalPresenter = new MenuPrincipalPresenter(menuPrincipalView, primaryStage);
        Platform.runLater(() -> primaryStage.getScene().setRoot(menuPrincipalView.getView()));
    }
}
