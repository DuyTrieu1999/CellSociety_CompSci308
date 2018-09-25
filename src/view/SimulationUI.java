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
import model.*;
import model.Cell;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ResourceBundle;
import java.util.Scanner;

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
        changeSpeed();
        myRoot.getChildren().add(myGridPane);
        //System.out.println(simulationName);
        return myScene;
    }
    public void step (double elapsedTime) {
        myGrid.updateGrid();
        gridSize = (int)sizeSlider.getVal();
    }
    private void changeSpeed () {
        speedSlider.setOnMouseDragged(event -> {
            animation.setRate(speedSlider.getVal());
            System.out.println(speedSlider.getVal());
        });
    }

    private void makeSlider () {
        sizeSlider = new SliderUI(myResources.getString("SizeLabel"),20, 10, 30);
        sizeSlider.setTextField();
        speedSlider = new SliderUI(myResources.getString("SetSpeed"), 1, 1, 2);
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
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, sizeSlider, speedSlider); //Omitted sizeSlider
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
        simulationName = name;
        setSimulation(simulationName);
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
        if (simuName.equals(myResources.getString("GOL")))
            myGrid = new Grid(gridSize);
        if (simuName.equals(myResources.getString("WaTor")))
            myGrid = new PredatorPreyGrid(gridSize);
        if (simuName.equals(myResources.getString("Fire")))
            myGrid = new FireGrid(gridSize);
        if (simuName.equals(myResources.getString("Segg")))
            myGrid = new SegGrid(gridSize);
        for (int i=0; i<myGrid.getRowNum();i++) {
            for(int j=0;j<myGrid.getColNum();j++) {
                Cell cell = myGrid.getCell(i,j);
                myGridPane.add(cell, i,j);
            }
        }
    }

    private String readSizeFromFile(String xmlString) {
        /*readFile
        File fXmlFile = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        DocumentBuilder.parse(new InputStream(new StringReader(fXmlFile.toString())));
        */
        System.out.println("Reading XML");
        Scanner scanner = new Scanner(xmlString);
        String s = scanner.nextLine();
        System.out.println(s);
        while(!s.contains("grid_size") && scanner.hasNextLine()) {
            s = scanner.nextLine();
            System.out.println(s);
        }
        if(!scanner.hasNextLine()) {
            return "";
        }
        String[] result1 = s.split("<grid_size>");
        String[] result2 = s.split("<");
        for(int i = 0; i < result2.length; i++) {
            System.out.println(result2[i]);
        }
        return result2[0];
    }
}
