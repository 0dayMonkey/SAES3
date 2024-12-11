package players.idjr.presenter;

import players.idjr.model.Reglages;
import players.idjr.view.ReglagesView;

public class ReglagesPresenter {
    private ReglagesView view;
    private Reglages model;

    public ReglagesPresenter(ReglagesView view) {
        this.view = view;
        this.model = new Reglages();
        initializeViewFromModel();
        setupEventHandlers();
    }

    private void initializeViewFromModel() {
        view.setLuminosite(model.getLuminosite());
        view.setVibration(model.isVibrationActive());
        view.setLangue(model.getLangue());
    }

    private void setupEventHandlers() {
        view.getLuminositeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            model.setLuminosite(newVal.doubleValue());
        });

        view.getVibrationToggle().switchOnProperty().addListener((obs, oldVal, newVal) -> {
            model.setVibrationActive(newVal);
        });

        view.getLanguesComboBox().setOnAction(e -> {
            model.setLangue(view.getLanguesComboBox().getValue());
        });
    }

    public void onRetourClicked() {
        // Logique pour revenir à l'écran principal
        System.out.println("Retour au menu principal");
    }
}