package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class SpeedSlider extends SliderUI {
    private Slider slider = new Slider();

    public SpeedSlider (String name, int val, int min, int max) {
        super(name, val, min, max);
    }
}
