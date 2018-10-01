package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class layouts the slider to control properties in the simulations
 * @author duytrieu
 */
public class SliderUI extends VBox {
    private Slider slider = new Slider();
    protected TextField valueText;
    protected Label nameLabel;
    protected HBox layoutBox;

    public SliderUI (String name, double val, double min, double max) {
        slider.setValue(val);
        slider.setMin(min);
        slider.setMax(max);
        slider.setMajorTickUnit((max-min)/8);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        slider.setPadding(new Insets(0, 10, 0, 10));

        nameLabel = new Label(name + ": ");
        valueText = new TextField(String.format("%.2f", slider.getValue()));

        layoutBox = new HBox();
        layoutBox.getChildren().addAll(slider, valueText);
        layoutBox.setPadding(new Insets(SceneENUM.HBOX_GRID.getVal(), 0, SceneENUM.HBOX_GRID.getVal(), 0));
        this.getChildren().add(nameLabel);
        this.getChildren().add(layoutBox);
    }
    public void setTextField () {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue)
            {
                valueText.textProperty().bind(slider.valueProperty().asString("%.0f"));
            }
        });
    }
    public double getVal () {
        return this.slider.getValue();
    }
    public Slider getSlider () {
        return slider;
    }
}
