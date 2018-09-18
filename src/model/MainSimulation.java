package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.SimulationUI;

public class MainSimulation extends Application{
    @Override
    public void start (Stage stage) {
        SimulationUI ui = new SimulationUI();
        stage.setTitle("Breakout Game");
        stage.setScene(ui.sceneInit());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
