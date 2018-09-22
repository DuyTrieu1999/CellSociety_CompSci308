package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model.
 * States:
 * FISH represents a cell occupied by fish
 * SHARK represents a cell occupied by a shark
 * WATER represents an empty cell in the sea
 * @author Austin Kao
 */

public class PredatorPreyCell extends Cell {
    private int reproductionTime;
    private int sharkEnergy;
    private final int REPRODUCTION_CYCLE_WAIT = 3;
    private final int MAX_SHARK_ENERGY = 1;
    private StateENUM[] states = {StateENUM.FISH, StateENUM.WATER, StateENUM.SHARK};

    public PredatorPreyCell(int row, int col, double width) {
        super(row, col, width);
    }
    public void updateCell () {
        return;
    }

    public void setReproductionTime(int fishMatingCycleWait) {
        reproductionTime = fishMatingCycleWait;
    }

    public void setSharkEnergy(int sharkStrength) {
        sharkEnergy = sharkStrength;
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case FISH:
                return Color.GREEN;
            case SHARK:
                return Color.GRAY;
            case WATER:
                return Color.BLUE;
            default:
                return null;
        }
    }

    @Override
    public void setStartState() {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
    }
}
