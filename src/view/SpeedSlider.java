package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class SpeedSlider extends SliderUI {

    public SpeedSlider (String name, int val, int min, int max) {
        super(name, val, min, max);
        this.valueText.setText(Integer.toString((int)this.getSlider().getValue()));
    }
    @Override
    public void setTextField () {
        this.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                valueText.setText(String.format("%.2f", newValue));
            }
        });
    }
}
