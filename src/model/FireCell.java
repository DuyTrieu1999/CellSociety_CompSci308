package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class FireCell extends Cell {

    private StateENUM[] states = {StateENUM.DEFORESTED, StateENUM.TREE, StateENUM.BURNING};
    private boolean hasNeighborFire;
    private double probCatch;

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
            this.setNextState(this.getCurrState());
        }
        this.setFill(this.getStateColor(this.getNextState()));
    }

    public void setProbCatch(double probability) {
        probCatch = probability;
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch(state) {
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

    @Override
    public void setStartState() {
        int rand = new Random().nextInt(this.states.length);
        this.setCurrState(this.states[rand]);
    }


}
