package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Samuel Appiah-Kubi
 */
public class FireCell extends Cell {
    private double probCatch = 0.15; //Probability of catching a fire
    private boolean hasNeighborFire; //Check for a neighboring cell on fire
    private StateENUM[] states = {StateENUM.DEFORESTED, StateENUM.TREE, StateENUM.BURNING};

    public FireCell(int row, int col, double width) {
        super(row, col, width);
        hasNeighborFire = false;
        probCatch = 0.15;
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
        } else {
            this.setNextState(StateENUM.DEFORESTED);
        }
        this.setFill(this.getStateColor(this.getNextState()));
    }

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
    public void setStartState() {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
    }


}
