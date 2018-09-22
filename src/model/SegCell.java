package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This cell represents a cell in the Schelling's model of segregation simulation.
 * States:
 * VACANT represents a cell with no agents that occupy the space
 * AGENT1, AGENT2 represent the two types of agents that will segregate
 * @author Austin Kao
 */

public class SegCell extends Cell{
    private boolean satisfied;
    private double myThreshold;
    private int numAlike;

    public SegCell(int row, int col, double width) {
        super(row, col, width);
        satisfied = true;
    }

    //For this simulation, will need to determine the individual satisfaction of cells before updating and moving cells.
    public void determineSatisfaction() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        numAlike = 0;
        for(Cell neighbor : currNeighbors) {
            if(this.getCurrState() == neighbor.getCurrState()) {
                numAlike++;
            }
        }
        if(numAlike > myThreshold*currNeighbors.size()) {
            satisfied = true;
        } else {
            satisfied = false;
        }
        updateCell();
    }
    // updateCell() assumes that there are only two types of agents
    public void updateCell() {
        if(!satisfied) {
            this.setCurrState(StateENUM.VACANT);
        } else {
            if(this.getCurrState() == StateENUM.AGENT1) {
                this.setNextState(StateENUM.AGENT2);
            } else if(this.getCurrState() == StateENUM.AGENT2) {
                this.setNextState(StateENUM.AGENT1);
            }
        }
    }

    public void setThreshold(double threshold) {
        myThreshold = threshold;
    }
}
