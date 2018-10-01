package view;
/**
 *
 * @author duytrieu
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SimuButton extends Button {

    public SimuButton (String buttonName, EventHandler<ActionEvent> event) {
        this.setText(buttonName);
        this.setMinWidth(SceneENUM.BUTTON_GRID.getVal());
        this.setMaxWidth(SceneENUM.BUTTON_GRID.getVal());
        this.setOnAction(event);
    }
}
