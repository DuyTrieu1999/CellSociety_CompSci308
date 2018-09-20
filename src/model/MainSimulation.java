package model;

import javafx.application.Application;
import javafx.stage.Stage;
import view.SimulationUI;

public class MainSimulation extends Application{
    @Override
    public void start (Stage stage) {
        SimulationUI ui = new SimulationUI();
        stage.setTitle("Cellular Automata Simulation");
        stage.setScene(ui.sceneInit());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
