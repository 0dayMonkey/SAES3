package players.idjr.presenter;

import ppti.view.GameInterfacePptiView;

import players.idjr.model.PseudoModel;
import players.idjr.view.MenuPrincipalView;
import players.idjr.view.ReglagesView;
import players.idjr.view.PseudoView;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MenuPrincipalPresenter {
    private MenuPrincipalView view;
    private Stage primaryStage;

    public MenuPrincipalPresenter(MenuPrincipalView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.getJouerButton().setOnAction(e -> handleJouer());
        view.getQuitterButton().setOnAction(e -> handleQuitter());
        view.getParametresButton().setOnAction(e -> handleParametres());
    }

    @SuppressWarnings("unused")
	private void handleJouer() {
        // Logique pour naviguer vers l'écran de choix des pseudo
       

    	PseudoView pseudoView = new PseudoView();
        PseudoModel pseudoModel = new PseudoModel();
        PseudoPresenter pseudoPresenter = new PseudoPresenter(pseudoView, pseudoModel, primaryStage);
        Platform.runLater(() -> primaryStage.getScene().setRoot(pseudoView.getView()));
       
    
    	/*
        // juste  pour test l interface PPTI 
        GameInterfacePptiView gameInterfaceView = new GameInterfacePptiView();
        Platform.runLater(() -> primaryStage.getScene().setRoot(gameInterfaceView.getView()));
       */ 
    }
    

    private void handleQuitter() {
        // Logique pour quitter l'application
        Platform.exit();
    }

    private void handleParametres() {
        // Logique pour afficher les réglages
        ReglagesView reglagesView = new ReglagesView(primaryStage);
        Scene reglagesScene = new Scene(reglagesView.getView(), 600, 400);
        Platform.runLater(() -> primaryStage.setScene(reglagesScene));
    }
}
