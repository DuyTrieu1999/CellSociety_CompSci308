package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This cell represents a cell in the Schelling's model of segregation simulation.
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
    // updateCell() assumes that there are two agents
    public void updateCell() {
        this.setPrevState(this.getCurrState());
        if(!satisfied) {
            this.setCurrState(0);
        } else {
            if(this.getCurrState() == 1) {
                this.setCurrState(2);
            } else if(this.getCurrState() == 2) {
                this.setCurrState(1);
            }
        }
    }

    public void setThreshold(double threshold) {
        myThreshold = threshold;
    }
}
