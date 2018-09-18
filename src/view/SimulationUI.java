package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SimulationUI {
    public static final Paint BACKGROUND = Color.AZURE;

    private Scene myScene;
    private Group myRoot;


    public Scene sceneInit () {
        myRoot = new Group();
        myScene = new Scene(myRoot, SceneENUM.SCENE_WIDTH.getVal(), SceneENUM.SCENE_HEIGHT.getVal(), BACKGROUND);
        makeAllButton();
        myScene.getStylesheets().add("./view/SimulationUIStyle.css");
        return myScene;
    }

    private void makeAllButton () {
        VBox buttonContainer = new VBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox1 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox2 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox3 = new HBox(SceneENUM.HBOX_GRID.getVal());
        HBox hbox4 = new HBox(SceneENUM.HBOX_GRID.getVal());
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
        SimuButton resetButton = makeButton("Reset", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetButtonHandler();
            }
        });
        hbox1.getChildren().add(cb);
        hbox2.getChildren().add(startButton);
        hbox3.getChildren().add(stopButton);
        hbox4.getChildren().add(resetButton);
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
        myRoot.getChildren().add(buttonContainer);
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
}
