package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model.
 * States:
 * FISH represents a cell occupied by fish
 * SHARK represents a cell occupied by a shark
 * SEA represents an empty cell in the sea
 * @author Austin Kao
 */

public class PredatorPreyCell extends Cell {
    private int reproductionTime;
    private int sharkEnergy;
    private final int REPRODUCTION_CYCLE_WAIT = 3;
    private final int MAX_SHARK_ENERGY = 1;

    public PredatorPreyCell(int row, int col, double width) {
        super(row, col, width);
    }
    @Override
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
    public void setStartState () {

    }
    @Override
    public Color getStateColor(StateENUM state) {
        switch(state) {
            case FISH:
                return Color.GREEN;
            case WATER:
                return Color.BLUE;
            case SHARK:
                return Color.GREY;
            default:
                return null;
        }
    }
}
