package view;

import javafx.scene.control.Button;

public class SimuButton extends Button {
    private String buttonName;

    public SimuButton (String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName () {
        return this.buttonName;
    }
}
