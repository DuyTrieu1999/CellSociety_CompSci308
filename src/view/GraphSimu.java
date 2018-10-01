package view;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.StateENUM;
import model.Grid;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class will generate a graph to represent the population in each simulation
 * @author Duy Trieu
 */
public class GraphSimu extends VBox {
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    private LineChart simuChart;
    private ArrayList<StateENUM> cellStates;
    private ArrayList<XYChart.Series<Number, Number>> dataArray = new ArrayList<>();
    private double sequence = 0;
    private double y = 10;
    private final int MAX_DATA_POINTS = 10;

    public GraphSimu (Grid myGrid) {
        TreeMap<StateENUM, Integer> populationMap = myGrid.getPopulationMap();
        cellStates = new ArrayList<>(populationMap.keySet());
        for (int i=0; i<cellStates.size(); i++) {
            dataArray.add(getSeries(cellStates.get(i).toString()));
        }
        simuChart.getData().addAll(dataArray);
        addLayout(myGrid.getSimDescription());
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
    public void addLayout (String description) {
        VBox layoutBox = new VBox();
        Text textarea = new Text(description);
        simuChart.setPrefWidth(SceneENUM.GRID_WIDTH.getVal());
        simuChart.setPrefHeight(SceneENUM.GRAPH_HEIGHT.getVal());
        layoutBox.getChildren().addAll(simuChart, textarea);
        layoutBox.setPadding(new Insets(SceneENUM.PADDING.getVal()));
        this.getChildren().add(layoutBox);
        this.setPadding(new Insets(SceneENUM.HBOX_GRID.getVal()));
        this.setLayoutX(SceneENUM.SCENE_WIDTH.getVal() - 2*SceneENUM.BUTTON_GRID.getVal());
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
