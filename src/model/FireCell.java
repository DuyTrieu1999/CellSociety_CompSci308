package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * This class implements the Spreading by Fire simulation.
 * States:
 * DEFORESTED represents a cell with no trees
 * TREE represents a cell with trees
 * BURNING represents a cell with trees that are currently burning
 * @author Austin Kao
 */

public class FireCell extends Cell {
    private StateENUM[] states = {StateENUM.TREE, StateENUM.DEFORESTED, StateENUM.BURNING};
    private double probCatch; //Probability of catching a fire
    private boolean hasNeighborFire; //Check for a neighboring cell on fire

    public FireCell (int row, int col, double width) {
        super(row, col, width);
        hasNeighborFire = false;
    }
    @Override
    public void updateCell() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for(Cell neighbor : currNeighbors) {
            if(neighbor.getCurrState() == StateENUM.BURNING) {
                hasNeighborFire = true;
            }
        }
        if(hasNeighborFire && this.getCurrState() == StateENUM.TREE) {
            double rn = Math.random();
            if(rn < probCatch) {
                this.setNextState(StateENUM.BURNING);
            }
        } else if(this.getCurrState() == StateENUM.BURNING) {
            this.setNextState(StateENUM.DEFORESTED);
        }
    }
    @Override
    public void setStartState () {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
    }
    @Override
    public Color getStateColor (StateENUM state) {
        switch (state) {
            case TREE:
                return Color.GREEN;
            case DEFORESTED:
                return Color.YELLOW;
            case BURNING:
                return Color.RED;
            default:
                return null;
        }
    }
    public void setProbCatch(double probability) {
        probCatch = probability;
    }
}
