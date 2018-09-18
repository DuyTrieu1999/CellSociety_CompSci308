package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
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
        VBox buttonContainer = new VBox(10);
        HBox hbox = new HBox(10);
        SimuButton startButton = new SimuButton("Play");
        setDimensions(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: add Start function
                System.out.println("start simulation");
            }
        });
        hbox.getChildren().add(startButton);
        buttonContainer.getChildren().add(hbox);
        myRoot.getChildren().add(buttonContainer);
    }

    private void setDimensions(SimuButton btn) {
        btn.setMinWidth(250 / 2);
        btn.setMaxWidth(250 / 2);
    }

}
