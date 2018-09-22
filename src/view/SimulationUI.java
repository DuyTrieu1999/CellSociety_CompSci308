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

/**
 *
 * @author duytrieu
 */
public class SimulationUI {
    public static final Paint BACKGROUND = Color.AZURE;
    public double FRAMES_PER_SECOND = 1;
    public double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;

    private Scene myScene;
    private Group myRoot;
    private GridPane myGridPane;
    private Grid myGrid;
    private Timeline animation = new Timeline();
    private KeyFrame frame;

    private boolean isStoped = true;

    private Insets buttonPane = new Insets((SceneENUM.SCENE_HEIGHT.getVal()-SceneENUM.GRID_HEIGHT.getVal()) / 2,
            SceneENUM.PADDING.getVal(),
            (SceneENUM.SCENE_WIDTH.getVal()-SceneENUM.GRID_WIDTH.getVal()) / 2,
            -SceneENUM.PADDING.getVal());

    private Insets cellPane = new Insets((SceneENUM.SCENE_HEIGHT.getVal()-SceneENUM.GRID_HEIGHT.getVal()) / 2,
            0,
            (SceneENUM.SCENE_WIDTH.getVal()-SceneENUM.GRID_WIDTH.getVal()) / 2,
            SceneENUM.PADDING.getVal());

    public Scene sceneInit () {
        frame  = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> this.step(SECOND_DELAY));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myRoot = new Group();
        myScene = new Scene(myRoot, SceneENUM.SCENE_WIDTH.getVal(), SceneENUM.SCENE_HEIGHT.getVal(), BACKGROUND);
        makeAllButton();
        myGrid = new Grid("Game of Life");
        addGridPane(myGrid);
        myRoot.getChildren().add(myGridPane);
//        myScene.getStylesheets().add("./view/SimulationUIStyle.css");
        return myScene;
    }
    public void step (double elapsedTime) {
        myGrid.updateGrid();
    }

    private void makeAllButton () {
        VBox buttonContainer = new VBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox1 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox2 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox3 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox4 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox5 = new HBox(SceneENUM.HBOX_GRID.getVal());
        ChoiceBox cb = makeChoiceBox();
        SimuButton startButton = makeButton("Play", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startButtonHandler();
            }
        });
        SimuButton stopButton = makeButton("Stop", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopButtonHandler();
            }
        });
        SimuButton stepButton = makeButton("Step", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stepButtonHandler();
            }
        });
        SimuButton resetButton = makeButton("Reset", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetButtonHandler();
            }
        });
        hbox1.getChildren().add(cb);
        hbox2.getChildren().add(startButton);
        hbox3.getChildren().add(stopButton);
        hbox4.getChildren().add(stepButton);
        hbox5.getChildren().addAll(resetButton);
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5);
        createButtonPane(buttonContainer);
    }

    public SimuButton makeButton (String buttonName, EventHandler<ActionEvent> event) {
        SimuButton button = new SimuButton(buttonName);
        setDimensions(button);
        button.setOnAction(event);
        return button;
    }

    private ChoiceBox makeChoiceBox () {
        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.getItems().add("Game of Life");
        cb.getItems().add("Wa-Tor World model");
        cb.getItems().add("Spreading of Fire");
        cb.getItems().add("Schelling's model of segregation");
        cb.setValue("Game of Life");
        cb.setOnAction(e -> getChoice(cb));
        return cb;
    }
    //TODO: Implement a better choice box since this is shitty af
    private void getChoice(ChoiceBox<String> cb) {
        String name = cb.getValue();
        System.out.println(name);
        if (name.equals("Game of Life")) {
            myRoot.getChildren().remove(myGridPane);
            System.out.println(myGrid);
        }
    }

    private void setDimensions(SimuButton btn) {
        btn.setMinWidth(SceneENUM.BUTTON_GRID.getVal());
        btn.setMaxWidth(SceneENUM.BUTTON_GRID.getVal());
    }

    private void startButtonHandler () {
        System.out.println("Start simulation");
        isStoped = false;
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.playFromStart();
    }
    private void stopButtonHandler () {
        System.out.println("Stop simulation");
        pauseSim();
    }
    private void resetButtonHandler () {
        System.out.println("Reset simulation");
        resetGrid();
    }
    private void stepButtonHandler () {
        System.out.println("Resume simulation");
        pauseSim();
        myGrid.updateGrid();
    }
    private void pauseSim () {
        animation.pause();
        isStoped = true;
    }
    private void resetGrid () {
        myRoot.getChildren().remove(myGridPane);
        addGridPane(myGrid);
    }
    private void createButtonPane (VBox buttonContainer) {
        buttonContainer.setPadding(buttonPane);
        buttonContainer.setMaxWidth(SceneENUM.BUTTON_GRID.getVal());
        buttonContainer.setMinWidth(SceneENUM.BUTTON_GRID.getVal());
        buttonContainer.setLayoutX(SceneENUM.SCENE_WIDTH.getVal() - SceneENUM.BUTTON_GRID.getVal());
        myRoot.getChildren().add(buttonContainer);
    }
    private void addGridPane (Grid grid) {
        myGridPane = new GridPane();
        for (int i=0; i< grid.getRowNum(); i++) {
            RowConstraints row = new RowConstraints(360/(grid.getRowNum()));
            myGridPane.getRowConstraints().add(row);
        }
        for (int i=0; i< grid.getColNum(); i++) {
            ColumnConstraints col = new ColumnConstraints(360/(grid.getColNum()));
            myGridPane.getColumnConstraints().add(col);
        }
        addCellToGrid();
        myGridPane.setPadding(new Insets(60,60,60,50));
    }
    private void addCellToGrid () {
        for (int i=0; i<myGrid.getRowNum();i++) {
            for(int j=0;j<myGrid.getColNum();j++) {
                Cell cell = myGrid.getCell(i,j);
                myGridPane.add(cell, i,j);
            }
        }
    }
}
