package view;

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
import model.Grid;
import model.Cell;

/**
 *
 * @author duytrieu
 */
public class SimulationUI {
    public static final Paint BACKGROUND = Color.AZURE;

    private Scene myScene;
    private Group myRoot;
    private GridPane myGridPane;
    private Grid myGrid;

    private Insets buttonPane = new Insets((SceneENUM.SCENE_HEIGHT.getVal()-SceneENUM.GRID_HEIGHT.getVal()) / 2,
            SceneENUM.PADDING.getVal(),
            (SceneENUM.SCENE_WIDTH.getVal()-SceneENUM.GRID_WIDTH.getVal()) / 2,
            -SceneENUM.PADDING.getVal());

    private Insets cellPane = new Insets((SceneENUM.SCENE_HEIGHT.getVal()-SceneENUM.GRID_HEIGHT.getVal()) / 2,
            0,
            (SceneENUM.SCENE_WIDTH.getVal()-SceneENUM.GRID_WIDTH.getVal()) / 2,
            SceneENUM.PADDING.getVal());

    public Scene sceneInit () {
        myRoot = new Group();
        myScene = new Scene(myRoot, SceneENUM.SCENE_WIDTH.getVal(), SceneENUM.SCENE_HEIGHT.getVal(), BACKGROUND);
        makeAllButton();
        myGrid = new Grid();
        myGrid.fillGrid();
        addGridPane(myGrid);
        myScene.getStylesheets().add("./view/SimulationUIStyle.css");
        System.out.println(myGridPane);
        return myScene;
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
        SimuButton resumeButton = makeButton("Resume", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resumeButtonHandler();
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
        hbox4.getChildren().add(resumeButton);
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
        ChoiceBox<String> cb = new ChoiceBox<String>();
        cb.getItems().add("Wa-Tor World model");
        cb.getItems().add("Spreading of Fire");
        cb.getItems().add("Schelling's model of segregation");
        cb.setValue("Wa-Tor World model");
        cb.setOnAction(e -> getChoice(cb));
        return cb;
    }
    //TODO: Implement a better choice box since this is shitty af
    private void getChoice(ChoiceBox<String> cb) {
        String name = cb.getValue();
        System.out.println(name);
    }

    private void setDimensions(SimuButton btn) {
        btn.setMinWidth(SceneENUM.BUTTON_GRID.getVal());
        btn.setMaxWidth(SceneENUM.BUTTON_GRID.getVal());
    }

    private void startButtonHandler () {
        System.out.println("Start simulation");
    }
    private void stopButtonHandler () {
        System.out.println("Stop simulation");
    }
    private void resetButtonHandler () {
        System.out.println("Reset simulation");
    }
    private void resumeButtonHandler () {
        System.out.println("Resume simulation");
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
        //myGridPane.setStyle("-fx-grid-lines-visible: " + sim.gridVisibility());
        myGridPane.setPadding(new Insets(60,60,60,50));
        myRoot.getChildren().add(myGridPane);
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
