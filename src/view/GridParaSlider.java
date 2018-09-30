package view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Grid;

import java.util.ArrayList;
import java.util.TreeMap;

public class GridParaSlider extends VBox {
    private ArrayList<SliderUI> sliders;

    public GridParaSlider(Grid grid) {
        TreeMap<String, Double> parameters = grid.getParameterValues();
        if (parameters == null) {
            return;
        }else {
            ArrayList<String> paraNames = new ArrayList<>(parameters.keySet());
            sliders = new ArrayList<>();
            for (int i = 0; i<parameters.size(); i++) {
                SliderUI slider = new SliderUI(paraNames.get(i), parameters.get(paraNames.get(i)), 0, 1 );
                slider.setTextField();
                sliders.add(slider);
                this.getChildren().addAll(slider);
            }
            this.setLayoutY(50);
        }
    }
    public ArrayList<SliderUI> getSliders () {
        return this.sliders;
    }
}
