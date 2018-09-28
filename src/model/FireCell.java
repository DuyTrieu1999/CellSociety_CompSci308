package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * This cell implements the Spreading by Fire simulation.
 * States:
 * DEFORESTED is land that does not have trees.
 * TREE is land that is filled with trees that are not on fire.
 * BURNING is land that is filled with burning trees.
 * @author Samuel Appiah-Kubi
 * @author Austin Kao
 */
public class FireCell extends Cell {
    private StateENUM[] states = {StateENUM.DEFORESTED, StateENUM.TREE, StateENUM.BURNING};
    private boolean hasNeighborFire; //Check for a neighboring cell on fire
    private double probCatch; //Probability of catching a fire

    public FireCell(int row, int col, double width) {
        super(row, col, width);
        hasNeighborFire = false;
    }

    @Override
    public void updateCell() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        hasNeighborFire = false;
        for(Cell neighbor : currNeighbors) {
            if(neighbor.getCurrState() == StateENUM.BURNING) {
                hasNeighborFire = true;
            }
        }
        if(hasNeighborFire && this.getCurrState() == StateENUM.TREE) {
            double rn = Math.random();
            if(rn < probCatch) {
                this.setNextState(StateENUM.BURNING);
            } else {
                this.setNextState(this.getCurrState());
            }
        } else if(this.getCurrState() == StateENUM.BURNING) {
            this.setNextState(StateENUM.DEFORESTED);
        } else {
            this.setNextState(this.getCurrState());
        }
        this.setFill(getStateColor(this.getNextState()));
    }

    @Override
    public void setProbCatch(double probability) {
        probCatch = probability;
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case DEFORESTED:
                return Color.YELLOW;
            case TREE:
                return Color.GREEN;
            case BURNING:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    @Override
    public void setRandStartState() {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
        this.setFill(getStateColor(this.getCurrState()));
    }
}
