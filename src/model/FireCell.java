package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class FireCell extends Cell {
    private double probCatch; //Probability of catching a fire
    private boolean hasNeighborFire; //Check for a neighboring cell on fire
    private StateENUM[] states = {StateENUM.DEFORESTED, StateENUM.TREE, StateENUM.BURNING};

    public FireCell(int row, int col, double width) {
        super(row, col, width);
        hasNeighborFire = false;
        probCatch = 0.6;
    }

    @Override
    public void updateCell() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for(Cell neighbor : currNeighbors) {
            if(neighbor.getCurrState() == StateENUM.BURNING) {
                this.hasNeighborFire = true;
            }
        }
        if(hasNeighborFire && this.getCurrState() == StateENUM.TREE) {
            double rn = Math.random();
            if(rn < probCatch) {
                this.setNextState(StateENUM.BURNING);
            } else {
                this.setNextState(StateENUM.TREE);
            }
            this.hasNeighborFire = false;
        } else if(this.getCurrState() == StateENUM.BURNING) {
            this.setNextState(StateENUM.DEFORESTED);
        } else {
            this.setNextState(this.getCurrState());
        }
        this.setFill(this.getStateColor(this.getNextState()));
    }
    @Override
    public void setStartState () {
        if (this.getColPos() == 0 || this.getRowPos() == 0) {
            this.setCurrState(states[2]);
            this.setFill(this.getStateColor(states[2]));
        } else {
            int rand = new Random().nextInt(states.length);
            this.setCurrState(states[rand]);
            this.setFill(this.getStateColor(this.getCurrState()));
        }
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

}