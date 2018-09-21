package view;
/**
 *
 * @author duytrieu
 */
import javafx.scene.control.Button;

public class SimuButton extends Button {
    private String buttonName;

    public SimuButton (String buttonName) {
        this.buttonName = buttonName;
        this.setText(buttonName);
    }

    public String getButtonName () {
        return this.buttonName;
    }
}
