package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import model.Grid;
import model.Cell;
import model.SegGrid;

import java.util.ResourceBundle;

/**
 *
 * @author duytrieu
 */
public class SimulationUI {
    public static final Paint BACKGROUND = Color.AZURE;
    public double FRAMES_PER_SECOND = 1;
    public double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    private Scene myScene;
    private Group myRoot;
    private GridPane myGridPane;
    private Grid myGrid;
    private SliderUI sizeSlider;
    private SliderUI speedSlider;
    private Timeline animation = new Timeline();
    private KeyFrame frame;
    private int gridSize;
    private String simulationName;
    private ResourceBundle myResources;

    private Insets buttonPane = new Insets((SceneENUM.SCENE_HEIGHT.getVal()-SceneENUM.GRID_HEIGHT.getVal()) / 2,
            SceneENUM.PADDING.getVal(),
            (SceneENUM.SCENE_WIDTH.getVal()-SceneENUM.GRID_WIDTH.getVal()) / 2,
            -SceneENUM.PADDING.getVal());

    public Scene sceneInit () {
        frame  = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> this.step(SECOND_DELAY));
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Button");
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myRoot = new Group();
        myScene = new Scene(myRoot, SceneENUM.SCENE_WIDTH.getVal(), SceneENUM.SCENE_HEIGHT.getVal(), BACKGROUND);
        makeAllButton();
        gridSize = (int)sizeSlider.getVal();
        addGridPane();
        simulationName = myResources.getString("GOL");
        addCellToGrid(simulationName);
        myRoot.getChildren().add(myGridPane);
        System.out.println(simulationName);
        return myScene;
    }
    public void step (double elapsedTime) {
        myGrid.updateGrid();
        gridSize = (int)sizeSlider.getVal();
    }

    private void makeSlider () {
        sizeSlider = new SliderUI(myResources.getString("SizeLabel"),15, 10, 20);
        sizeSlider.setTextField();
        speedSlider = new SliderUI(myResources.getString("SetSpeed"), 1, 0.5, 2);
        speedSlider.setTextField();
    }

    private void makeAllButton () {
        VBox buttonContainer = new VBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox1 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox2 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox3 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox4 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox5 = new HBox(SceneENUM.HBOX_GRID.getVal());
        ChoiceBox cb = makeChoiceBox();
        SimuButton startButton = new SimuButton(myResources.getString("Play"), event -> startButtonHandler());
        SimuButton stopButton = new SimuButton(myResources.getString("Stop"), event -> stopButtonHandler());
        SimuButton stepButton = new SimuButton(myResources.getString("Step"), event -> stepButtonHandler());
        SimuButton resetButton = new SimuButton(myResources.getString("Reset"), event -> resetButtonHandler());
        makeSlider();
        hbox1.getChildren().add(cb);
        hbox2.getChildren().add(startButton);
        hbox3.getChildren().add(stopButton);
        hbox4.getChildren().add(stepButton);
        hbox5.getChildren().addAll(resetButton);
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, sizeSlider, speedSlider);
        createButtonPane(buttonContainer);
    }

    private ChoiceBox makeChoiceBox () {
        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.getItems().add(myResources.getString("GOL"));
        cb.getItems().add(myResources.getString("WaTor"));
        cb.getItems().add(myResources.getString("Fire"));
        cb.getItems().add(myResources.getString("Segg"));
        cb.setValue(myResources.getString("GOL"));
        cb.setOnAction(e -> getChoice(cb));
        return cb;
    }
    private void getChoice(ChoiceBox<String> cb) {
        String name = cb.getValue();
        if (name.equals(myResources.getString("GOL"))) {
            simulationName = myResources.getString("GOL");
            setSimulation(simulationName);
        }
        else if (name.equals(myResources.getString("WaTor"))) {
            simulationName = myResources.getString("WaTor");
            System.out.println(simulationName);
            setSimulation(simulationName);
        }
        else if (name.equals(myResources.getString("Fire"))) {
            simulationName = myResources.getString("Fire");
            System.out.println(simulationName);
            setSimulation(simulationName);
        }
        else {
            simulationName = myResources.getString("Segg");
            System.out.println(simulationName);
            setSimulation(simulationName);
        }
    }

    private void setSimulation (String simuName) {
        myRoot.getChildren().remove(myGridPane);
        addGridPane();
        addCellToGrid(simuName);
        myRoot.getChildren().add(myGridPane);
        pauseSim();
    }
    private void startButtonHandler () {
        startSim();
    }
    private void stopButtonHandler () {
        pauseSim();
    }
    private void resetButtonHandler () {
        setSimulation(simulationName);
    }
    private void stepButtonHandler () {
        pauseSim();
        myGrid.updateGrid();
    }
    private void startSim () {
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.playFromStart();
    }
    private void pauseSim () {
        animation.pause();
    }
    private void createButtonPane (VBox buttonContainer) {
        buttonContainer.setPadding(buttonPane);
        buttonContainer.setMaxWidth(SceneENUM.BUTTON_GRID.getVal());
        buttonContainer.setMinWidth(SceneENUM.BUTTON_GRID.getVal());
        buttonContainer.setLayoutX(SceneENUM.SCENE_WIDTH.getVal() - SceneENUM.BUTTON_GRID.getVal());
        myRoot.getChildren().add(buttonContainer);
    }
    private void addGridPane () {
        myGridPane = new GridPane();
        for (int i=0; i< gridSize; i++) {
            RowConstraints row = new RowConstraints(360/gridSize);
            myGridPane.getRowConstraints().add(row);
        }
        for (int i=0; i< gridSize; i++) {
            ColumnConstraints col = new ColumnConstraints(360/gridSize);
            myGridPane.getColumnConstraints().add(col);
        }
        myGridPane.setPadding(new Insets(60,60,60,50));
    }
    private void addCellToGrid (String simuName) {
        myGrid = new Grid(simuName, gridSize);
        for (int i=0; i<myGrid.getRowNum();i++) {
            for(int j=0;j<myGrid.getColNum();j++) {
                Cell cell = myGrid.getCell(i,j);
                myGridPane.add(cell, i,j);
            }
        }
    }
}
