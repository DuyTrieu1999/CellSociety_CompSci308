package view;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.StateENUM;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class will generate a graph to represent the population in each simulation
 * @author Duy Trieu
 */
public class GraphSimu extends VBox {
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    public LineChart simuChart;
    private ArrayList<StateENUM> cellStates;
    private ArrayList<XYChart.Series<Number, Number>> dataArray = new ArrayList<>();
    private double sequence = 0;
    private double y = 10;
    private final int MAX_DATA_POINTS = 10;
    protected HBox layoutBox;

    public GraphSimu (TreeMap<StateENUM, Integer> populationMap) {
        cellStates = new ArrayList<>(populationMap.keySet());
        for (int i=0; i<cellStates.size(); i++) {
            dataArray.add(getSeries(cellStates.get(i).toString()));
        }
        simuChart.getData().addAll(dataArray);
        addLayout();
        updateGraph(populationMap);
    }
    public XYChart.Series<Number, Number> getSeries (String name) {
        simuChart = new LineChart<>(xAxis, yAxis);
        yAxis.setLabel("Population");
        xAxis.setForceZeroInRange(false);
        xAxis.setLabel("Simulation timeline");
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, null, "Cells"));

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        series.getData().add(new XYChart.Data<>(++sequence, y));
        return series;
    }
    public void addLayout () {
        layoutBox = new HBox();
        layoutBox.getChildren().add(simuChart);
        layoutBox.setPadding(new Insets(SceneENUM.PADDING.getVal()));
        this.getChildren().add(layoutBox);
        this.setPadding(new Insets(SceneENUM.HBOX_GRID.getVal()));
        this.setLayoutX(SceneENUM.SCENE_WIDTH.getVal() - 2.3*SceneENUM.BUTTON_GRID.getVal());
    }

    public void updateGraph (TreeMap<StateENUM, Integer> populationMap) {
        for (int i=0; i<populationMap.size(); i++) {
            dataArray.get(i).getData().add(new XYChart.Data<>(++sequence, populationMap.get(cellStates.get(i))));
            if (sequence > MAX_DATA_POINTS) {
                dataArray.get(i).getData().remove(0);
            }
            if (sequence > MAX_DATA_POINTS - 1) {
                xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                xAxis.setUpperBound(xAxis.getUpperBound() + 1);
            }
        }
    }
}
