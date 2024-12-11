package players.idjr.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ToggleSwitch extends StackPane {
    private final BooleanProperty switchedOn = new SimpleBooleanProperty(false);

    public ToggleSwitch() {
        Rectangle background = new Rectangle(30, 10);
        background.setArcWidth(10);
        background.setArcHeight(10);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);

        Circle trigger = new Circle(10);
        trigger.setCenterX(10);
        trigger.setCenterY(10);
        trigger.setFill(Color.WHITE);
        trigger.setStroke(Color.BLACK);

        getChildren().addAll(background, trigger);

        switchedOn.addListener((obs, oldState, newState) -> {
            if (newState) {
                trigger.setCenterX(20);
                background.setFill(Color.LIGHTGREEN);
            } else {
                trigger.setCenterX(10);
                background.setFill(Color.WHITE);
            }
        });

        setOnMouseClicked(event -> switchedOn.set(!switchedOn.get()));
    }

    public BooleanProperty switchOnProperty() { return switchedOn; }

    public boolean isSelected() {
        return switchedOn.get();
    }

    public void setSelected(boolean selected) {
        switchedOn.set(selected);
    }
}