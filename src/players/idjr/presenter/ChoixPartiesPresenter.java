package players.idjr.presenter;

import players.idjr.model.Partie;
import players.idjr.model.ChoixPartiesModel;
import players.idjr.model.PartieLoadingModel;
import players.idjr.view.ChoixPartiesView;
import players.idjr.view.PseudoView;
import players.idjr.model.PseudoModel;
import players.idjr.presenter.PseudoPresenter;
import javafx.application.Platform;
import javafx.stage.Stage;
import players.data.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChoixPartiesPresenter {
    private ChoixPartiesView view;
    private Stage primaryStage;
    private ChoixPartiesModel model;
    private String pseudo;
    private volatile boolean running = true;

    public ChoixPartiesPresenter(ChoixPartiesView view, ChoixPartiesModel model, Stage primaryStage, String pseudo) {
        this.view = view;
        this.model = model;
        this.primaryStage = primaryStage;
        this.pseudo = pseudo;
        setupEventHandlers();
        setListeParties(model.getListeParties());
    }

    private void setupEventHandlers() {
        view.setRetourAction(this::handleRetour);
    }

    private void handleRetour() {
        PseudoView pseudoView = new PseudoView();
        PseudoModel pseudoModel = new PseudoModel();
        PseudoPresenter pseudoPresenter = new PseudoPresenter(pseudoView, pseudoModel, primaryStage);
        Platform.runLater(() -> primaryStage.getScene().setRoot(pseudoView.getView()));
    }

    // Mise à jour des données de la vue depuis le modèle
    public void setListeParties(Map<String, Partie> listeParties) {
        view.clearParties(); // Efface avant de réajouter

        for (Map.Entry<String, Partie> entry : listeParties.entrySet()) {
            Partie partie = entry.getValue();

            if (partie.getStatut() == null || partie.getStatut().isEmpty()) {
                continue; // Passe à la partie suivante
            }

            String statutPartie = partie.getStatut();

            if (statutPartie.equals("ATTENTE")) {
                view.ajouterPartie(partie, () -> handleRejoindrePartie(partie));
            }
            // Pas besoin de supprimer les parties ici, car la liste est effacée au début
        }
    }

    private void handleRejoindrePartie(Partie partie) {
        LinkedHashMap<String, String> attributs = new LinkedHashMap<>();
        attributs.put("nomJ", this.pseudo);
        attributs.put("identite", "JR");
        attributs.put("idp", partie.getIdp());

        String message = Data.writeMessage("DCP", attributs);
        Data.tcpSender(partie.getIp(), 7777, message);

        PartieLoadingModel partieLoadingModel = new PartieLoadingModel();
        new ChoixPartieLoadingPresenter(this.view, partieLoadingModel, this.model);
    }

    public void startListeningForGames(String localInterface) {
        new Thread(() -> {
            Data.udpReceiver(localInterface, (message) -> {
                Object[] parsedMessage = Data.readMessage(message);
                String key = (String) parsedMessage[0];
                @SuppressWarnings("unchecked")
                HashMap<String, String> attributes = (HashMap<String, String>) parsedMessage[1];

                if ("ACP".equals(key) || "AMAJP".equals(key)) {
                    Platform.runLater(() -> {
                        updateGameList(attributes);
                    });
                }
            });
            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void updateGameList(HashMap<String, String> attributes) {
        String nomp = attributes.get("nomp");
        String idp = attributes.get("idp");
        String ip = attributes.get("ip");
        String statut = attributes.get("statut");
        
        Partie partie = new Partie(idp, nomp, ip, statut);

        if (statut.equals("ATTENTE")) {
            Partie existingPartie = model.getPartie(idp);
            if (existingPartie != null) {
                model.mettreAJourPartie(partie);
            } else {
                model.ajouterPartie(partie);
            }
        } else if (statut.equals("COMPLETE") || statut.equals("ANNULEE")) {
            model.supprimerPartie(idp);
        }

        // Mise à jour de la vue depuis le modèle
        setListeParties(model.getListeParties());
    }

    // Méthode pour arrêter proprement l'écoute des jeux
    public void stopListening() {
        running = false;
    }
}
