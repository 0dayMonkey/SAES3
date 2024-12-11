package players.idjr.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import players.idjr.view.MenuPrincipalView;
import players.idjr.presenter.MenuPrincipalPresenter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            MenuPrincipalView menuPrincipalView = new MenuPrincipalView();
            MenuPrincipalPresenter presenter = new MenuPrincipalPresenter(menuPrincipalView, primaryStage);

            Scene scene = new Scene(menuPrincipalView.getView(), 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Un mouton à la mer");
            primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}