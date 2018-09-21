package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
/**
 *
 * @author duytrieu
 */
public class SliderUI extends Slider {
    private static final int start = 10;
    private static final int finish = 20;
    public SliderUI () {
        this.setMax(finish);
        this.setMin(start);
        this.setValue((finish+start) / 2);
    }
    public int getSliderInfo () {
        this.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });
        return 0;
    }
}
