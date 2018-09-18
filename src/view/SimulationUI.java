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
    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 700;
    public static final Paint BACKGROUND = Color.AZURE;

    private Scene myScene;
    private Group myRoot;


    public Scene sceneInit () {
        myRoot = new Group();
        myScene = new Scene(myRoot, SIZE_X, SIZE_Y, BACKGROUND);
        makeButton();
        myScene.getStylesheets().add("view/SimulationUIStyle.css");
        return myScene;
    }

    private void makeButton () {
        // TODO: add spacing for VBox and HBox, set default to be 10
        VBox buttonContainer = new VBox(10);
        HBox hbox1 = new HBox(10);
        HBox hbox2 = new HBox(10);
        HBox hbox3 = new HBox(10);
        HBox hbox4 = new HBox(10);
        ChoiceBox cb = makeChoiceBox();
        SimuButton startButton = new SimuButton("Play");
        setDimensions(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: add Start function
                System.out.println("start simulation");
            }
        });
        SimuButton stopButton = new SimuButton("Pause");
        setDimensions(stopButton);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: add Stop function
                System.out.println("Stop simulation");
            }
        });
        SimuButton resetButton = new SimuButton("Reset");
        setDimensions(resetButton);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: add Reset function
                System.out.println("Reset current simulation");
            }
        });
        hbox1.getChildren().add(cb);
        hbox2.getChildren().add(startButton);
        hbox3.getChildren().add(stopButton);
        hbox4.getChildren().add(resetButton);
        buttonContainer.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
        myRoot.getChildren().add(buttonContainer);
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
        // TODO: Set size for buttons. Set default to 240
        btn.setMinWidth(240);
        btn.setMaxWidth(240);
    }

}
