package players.idjr.presenter;

import players.idjr.model.ChoixPartiesModel;
import players.idjr.model.PartieLoadingModel;
import players.idjr.view.ChoixPartiesView;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.LinkedHashMap;

public class ChoixPartieLoadingPresenter {
    private final ChoixPartiesView view;
    private final PartieLoadingModel partieLoadingModel;
    private final ChoixPartiesModel partieModel;
    private boolean accepted = false;

    public ChoixPartieLoadingPresenter(ChoixPartiesView view, PartieLoadingModel partieLoadingModel, ChoixPartiesModel partieModel) {
        this.view = view;
        this.partieLoadingModel = partieLoadingModel;
        this.partieModel = partieModel;
        setTransition();
    }

    private void setTransition() {
        partieLoadingModel.recevoirMessagesTCP(7777, (messageList, playerIP) -> {
            Object messageType = messageList[0];
            LinkedHashMap<String, String> attributs = (LinkedHashMap<String, String>) messageList[1];

            if ("ADP".equals(messageType)) {
                handleADP(attributs);
            } else if ("RDP".equals(messageType)) {
                handleRDP(attributs);
            }else if ("IP".equals(messageType) && this.accepted==true) {
                System.out.println(attributs);
            }
        });
    }

    private void handleADP(LinkedHashMap<String, String> attributs) {
        String idp = attributs.get("idp");
        String nomPartie = partieModel.getNomPartie(idp);
        this.accepted=true;

        // Mettre à jour la vue avec un message d'acceptation
        Platform.runLater(() -> {
            view.modifierPartie(nomPartie, new Label("Accepté, veuillez attendre le lancement de la partie"));
        });
    }

    /**
     * Gère le message de refus ou d'exclusion de partie ("RDP").
     *
     * @param attributs Les attributs du message.
     */
    private void handleRDP(LinkedHashMap<String, String> attributs) {
        String idp = attributs.get("idp");
        String nomPartie = partieModel.getNomPartie(idp);
        this.accepted=false;
        
        // Mettre à jour la vue avec un message de refus
        Platform.runLater(() -> {
            view.modifierPartie(nomPartie, new Label("Refusé / Exclu"));
        });
    }
}
