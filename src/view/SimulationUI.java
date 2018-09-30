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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.Cell;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.TreeMap;

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
    private static final String GAME_OF_LIFE_XML = "Game_Of_Life.xml";
    private static final String WA_TOR_WORLD_XML = "WaTor.xml";
    private static final String SCHELLING_SEGREGATION_XML = "Segregation.xml";
    private static final String SPREADING_FIRE_XML = "Spreading_fire.xml";
    private static final String ERROR_TESTING = "XMLErrorTesting.xml";
    private static final double MAX_GRID_PANE_SIZE = 360;
    private static final String RESOURCE_PATH = "data";
    private static final String DEFAULT_FILE_NAME = "";

    protected RadioButton rectangleCellButton;
    protected RadioButton triangleCellButton;
    protected RadioButton hexagonCellButton;
    protected HBox radioBox;
    protected ToggleGroup radioGroup;

    private Scene myScene;
    private Group myRoot;
    private GridPane myGridPane;
    private Grid myGrid;
    private VBox buttonContainer = new VBox(SceneENUM.HBOX_GRID.getVal());
    private SliderUI sizeSlider;
    private SliderUI speedSlider;
    private Timeline animation = new Timeline();
    private KeyFrame frame;
    private int gridSize;
    private String simulationName;
    private GraphSimu simulationGraph;
    private String cellType;
    private XMLSaveBuilder xmlSave;

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
        xmlSave = new XMLSaveBuilder();
        makeCellTypeButton();
        makeAllButton();
        getCellType();
        gridSize = (int) sizeSlider.getVal();
        addGridPane();
        simulationName = myResources.getString("GOL");
        addCellToGrid(simulationName, DEFAULT_FILE_NAME);
        makeSaveButton();
        makeLoadButton();
        myRoot.getChildren().add(buttonContainer);
        changeSpeed();
        TreeMap<StateENUM, Integer> simuMap = myGrid.getPopulationMap();
        simulationGraph = new GraphSimu(simuMap);
        myRoot.getChildren().add(simulationGraph);
        myRoot.getChildren().add(myGridPane);
        return myScene;
    }
    public void step (double elapsedTime) {
        myGrid.updateGrid();
        gridSize = (int)sizeSlider.getVal();
        TreeMap<StateENUM, Integer> simuMap = myGrid.getPopulationMap();
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
        hbox5.getChildren().add(resetButton);
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, sizeSlider, speedSlider, radioBox);
        createButtonPane(buttonContainer);
    }
    private void makeSaveButton () {
        HBox hbox6 = new HBox(SceneENUM.HBOX_GRID.getVal());
        SimuButton saveButton = new SimuButton(myResources.getString("Save"), event -> saveFileChooser());
        hbox6.getChildren().add(saveButton);
        buttonContainer.getChildren().add(hbox6);
    }
    private void makeLoadButton () {
        HBox hbox7 = new HBox(SceneENUM.HBOX_GRID.getVal());
        SimuButton loadButton = new SimuButton(myResources.getString("Load"), event -> openFileChooser());
        hbox7.getChildren().add(loadButton);
        buttonContainer.getChildren().add(hbox7);
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
        setSimulation(simulationName, DEFAULT_FILE_NAME);
    }

    private void setSimulation (String simuName, String filename) {
        myRoot.getChildren().remove(myGridPane);
        myRoot.getChildren().remove(simulationGraph);
        addGridPane();
        addCellToGrid(simuName, filename);
        TreeMap<StateENUM, Integer> simuMap = myGrid.getPopulationMap();
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
        setSimulation(simulationName, DEFAULT_FILE_NAME);
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
    private void addCellToGrid (String simuName, String filename) {
        getCellType();
        if (simuName.equals(myResources.getString("GOL"))) {
            if(filename.equals(DEFAULT_FILE_NAME)) {
                myGrid = new Grid(GAME_OF_LIFE_XML, gridSize, cellType);
            } else {
                myGrid = new Grid(filename, gridSize, cellType);
            }
        }
        if (simuName.equals(myResources.getString("WaTor"))) {
            if(filename.equals(DEFAULT_FILE_NAME)) {
                myGrid = new PredatorPreyGrid(WA_TOR_WORLD_XML, gridSize, cellType);
            } else {
                myGrid = new PredatorPreyGrid(filename, gridSize, cellType);
            }
        }
        if (simuName.equals(myResources.getString("Fire"))) {
            if(filename.equals(DEFAULT_FILE_NAME)) {
                myGrid = new FireGrid(SPREADING_FIRE_XML, gridSize, cellType);
            } else {
                myGrid = new FireGrid(filename, gridSize, cellType);
            }
        }
        if (simuName.equals(myResources.getString("Segg"))) {
            if(filename.equals(DEFAULT_FILE_NAME)) {
                myGrid = new SegGrid(SCHELLING_SEGREGATION_XML, gridSize, cellType);
            } else {
                myGrid = new SegGrid(filename, gridSize, cellType);
            }
        }
        for (int i=0; i<myGrid.getRowNum();i++) {
            for(int j=0;j<myGrid.getColNum();j++) {
                Cell cell = myGrid.getCell(i,j);
                myGridPane.add(cell, i,j);
            }
        }
    }
    private void saveFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File As");
        File defaultFile = new File(RESOURCE_PATH);
        fileChooser.setInitialDirectory(defaultFile);
        File file = fileChooser.showSaveDialog(new Stage());
        System.out.println(myGrid.getSimType());
        if (file != null) {
            try {
                xmlSave.createSave(file.getPath(), myGrid.getSimType(), gridSize, myGrid.getParameterValues(), myGrid.createSaveState());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File defaultFile = new File(RESOURCE_PATH);
        fileChooser.setInitialDirectory(defaultFile);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                myGrid.changeConfig(file.getName());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        gridSize = myGrid.getSize();
        simulationName = myGrid.getSimType();
        setSimulation(simulationName, file.getName());
    }
}
