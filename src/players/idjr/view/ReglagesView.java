package players.idjr.view;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import players.idjr.presenter.MenuPrincipalPresenter;
import players.idjr.presenter.ReglagesPresenter;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ReglagesView {
    private BorderPane root;
    private Slider luminositeSlider;
    private ToggleSwitch vibrationToggle;
    private ComboBox<String> languesComboBox;
    private ReglagesPresenter presenter;
    private Stage primaryStage;

    public ReglagesView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        creerVue();
        presenter = new ReglagesPresenter(this);
    }

    private void creerVue() {
        root = new BorderPane();
        
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label titre = new Label("Réglages");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        luminositeSlider = new Slider(0, 100, 50);
        luminositeSlider.setShowTickLabels(false);
        luminositeSlider.setShowTickMarks(false);
        luminositeSlider.setStyle("-fx-control-inner-background: lightgray;");
        HBox luminositeBox = new HBox(10, new Label("Luminosité"), luminositeSlider);
        luminositeBox.setAlignment(Pos.CENTER_LEFT);

        vibrationToggle = new ToggleSwitch();
        HBox vibrationBox = new HBox(10, new Label("Vibration"), vibrationToggle);
        vibrationBox.setAlignment(Pos.CENTER_LEFT);

        languesComboBox = new ComboBox<>();
        languesComboBox.getItems().addAll("Français", "Anglais", "Espagnol", "Italien", "Allemand", "Arabe");
        languesComboBox.setValue("Français");
        languesComboBox.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 2px; -fx-font-size: 16px; -fx-pref-width: 150px; -fx-pref-height: 40px;");
        HBox languesBox = new HBox(10, new Label("Langues"), languesComboBox);
        languesBox.setAlignment(Pos.CENTER_LEFT);

        content.getChildren().addAll(titre, luminositeBox, vibrationBox, languesBox);

        Button retourButton = new Button("Retour");
        retourButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 2px; -fx-font-size: 16px; -fx-pref-width: 150px; -fx-pref-height: 40px;");
        retourButton.setOnAction(e -> retourMenuPrincipal());

        VBox topContent = new VBox(retourButton);
        topContent.setPadding(new Insets(20, 20, 0, 20));
        root.setTop(topContent);
        root.setCenter(content);
    }

    private void retourMenuPrincipal() {
        MenuPrincipalView menuPrincipalView = new MenuPrincipalView();
        MenuPrincipalPresenter presenter = new MenuPrincipalPresenter(menuPrincipalView, primaryStage);

        Scene scene = new Scene(menuPrincipalView.getView(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public BorderPane getView() {
        return root;
    }

    public Slider getLuminositeSlider() {
        return luminositeSlider;
    }

    public ToggleSwitch getVibrationToggle() {
        return vibrationToggle;
    }

    public ComboBox<String> getLanguesComboBox() {
        return languesComboBox;
    }

    public void setLuminosite(double valeur) {
        luminositeSlider.setValue(valeur);
    }

    public void setVibration(boolean active) {
        vibrationToggle.setSelected(active);
    }

    public void setLangue(String langue) {
        languesComboBox.setValue(langue);
    }
}