package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import model.*;
import model.Cell;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 *
 * @author duytrieu
 * IDEA: When main launches, choose the configuration file to use right away. This will automatically specify the grid sie and type of shape.
 * EXTRA IDEA: When main launches, bring the user to a start screen where the XML File can be selected and have a launch button to then bring
 * the user to the main UI screen.
 */
public class SimulationUI {
    private static final Paint BACKGROUND = Color.AZURE;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private double FRAMES_PER_SECOND = 1;
    private double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;
    private static final String GAME_OF_LIFE_XML = "TestSave.xml";
    private static final String WA_TOR_WORLD_XML = "WaTor.xml";
    private static final String SCHELLING_SEGREGATION_XML = "Segregation.xml";
    private static final String SPREADING_FIRE_XML = "Spreading_fire.xml";
    private static final double MAX_GRID_PANE_SIZE = 360;

    protected RadioButton rectangleCellButton;
    protected RadioButton triangleCellButton;
    protected RadioButton hexagonCellButton;
    protected HBox radioBox;
    protected ToggleGroup radioGroup;

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
    private GraphSimu simulationGraph;
    private String cellType;

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
        makeCellTypeButton();
        makeAllButton();
        getCellType();
        gridSize = (int) sizeSlider.getVal();
        addGridPane();
        simulationName = myResources.getString("GOL");
        addCellToGrid(simulationName);
        changeSpeed();
        HashMap<StateENUM, Integer> simuMap = myGrid.getPopulationMap();
        simulationGraph = new GraphSimu(simuMap);
        myRoot.getChildren().add(simulationGraph);
        myRoot.getChildren().add(myGridPane);
        return myScene;
    }
    public void step (double elapsedTime) {
        myGrid.updateGrid();
        gridSize = (int)sizeSlider.getVal();
        HashMap<StateENUM, Integer> simuMap = myGrid.getPopulationMap();
        simulationGraph.updateGraph(simuMap);
    }
    private void changeSpeed () {
        speedSlider.setOnMouseDragged(event -> {
            animation.setRate(speedSlider.getVal());
        });
    }

    private void makeSlider () {
        sizeSlider = new SliderUI(myResources.getString("SizeLabel"),SceneENUM.SIZE_SLIDER_VAL.getVal(),
                SceneENUM.SIZE_SLIDER_MIN.getVal(), SceneENUM.SIZE_SLIDER_MAX.getVal());
        sizeSlider.setTextField();
        speedSlider = new SliderUI(myResources.getString("SetSpeed"), SceneENUM.SPEED_SLIDER_VAL.getVal(),
                SceneENUM.SPEED_SLIDER_MIN.getVal(), SceneENUM.SPEED_SLIDER_MAX.getVal());
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
        changeSpeed();
        hbox1.getChildren().add(cb);
        hbox2.getChildren().add(startButton);
        hbox3.getChildren().add(stopButton);
        hbox4.getChildren().add(stepButton);
        hbox5.getChildren().addAll(resetButton);
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, sizeSlider, speedSlider, radioBox);
        createButtonPane(buttonContainer);
    }

    private void makeCellTypeButton () {
        rectangleCellButton = new RadioButton(myResources.getString("Rectangle"));
        rectangleCellButton.setSelected(true);
        triangleCellButton = new RadioButton(myResources.getString("Triangle"));
        hexagonCellButton = new RadioButton(myResources.getString("Hexagon"));

        radioGroup = new ToggleGroup();
        radioBox = new HBox(SceneENUM.HBOX_GRID.getVal());
        radioBox.getChildren().add(rectangleCellButton);
        rectangleCellButton.setToggleGroup(radioGroup);
        radioBox.getChildren().add(triangleCellButton);
        triangleCellButton.setToggleGroup(radioGroup);
        radioBox.getChildren().add(hexagonCellButton);
        hexagonCellButton.setToggleGroup(radioGroup);
    }
    private void getCellType () {
        if (rectangleCellButton.isSelected()) {
            cellType = "Rectangle";
        }
        if (triangleCellButton.isSelected()) {
            cellType = "Triangle";
        }
        if (hexagonCellButton.isSelected()) {
            cellType = "Hexagon";
        }
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
        simulationName = name;
        setSimulation(simulationName);
    }

    private void setSimulation (String simuName) {
        myRoot.getChildren().remove(myGridPane);
        myRoot.getChildren().remove(simulationGraph);
        addGridPane();
        addCellToGrid(simuName);
        HashMap<StateENUM, Integer> simuMap = myGrid.getPopulationMap();
        simulationGraph = new GraphSimu(simuMap);
        myRoot.getChildren().add(myGridPane);
        myRoot.getChildren().add(simulationGraph);
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
        simulationGraph.updateGraph(myGrid.getPopulationMap());
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
        buttonContainer.setLayoutX(SceneENUM.SCENE_WIDTH.getVal() - 3*SceneENUM.BUTTON_GRID.getVal());
        myRoot.getChildren().add(buttonContainer);
    }
    private void addGridPane () {
        myGridPane = new GridPane();
        for (int i=0; i< gridSize; i++) {
            RowConstraints row = new RowConstraints(MAX_GRID_PANE_SIZE/gridSize);
            myGridPane.getRowConstraints().add(row);
        }
        for (int i=0; i< gridSize; i++) {
            ColumnConstraints col = new ColumnConstraints(MAX_GRID_PANE_SIZE/gridSize);
            myGridPane.getColumnConstraints().add(col);
        }
        myGridPane.setPadding(new Insets(SceneENUM.GRID_PANE_PADDING.getVal()));
    }
    private void addCellToGrid (String simuName) {
        getCellType();
        if (simuName.equals(myResources.getString("GOL")))
            myGrid = new Grid(GAME_OF_LIFE_XML, gridSize, cellType);
        if (simuName.equals(myResources.getString("WaTor")))
            myGrid = new PredatorPreyGrid(WA_TOR_WORLD_XML, gridSize, cellType);
        if (simuName.equals(myResources.getString("Fire")))
            myGrid = new FireGrid(SPREADING_FIRE_XML, gridSize, cellType);
        if (simuName.equals(myResources.getString("Segg")))
            myGrid = new SegGrid(SCHELLING_SEGREGATION_XML, gridSize, cellType);
        for (int i=0; i<myGrid.getRowNum();i++) {
            for(int j=0;j<myGrid.getColNum();j++) {
                Cell cell = myGrid.getCell(i,j);
                myGridPane.add(cell, i,j);
            }
        }
    }
}
