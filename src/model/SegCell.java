package model;
package view;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This cell represents a cell in the Schelling's model of segration simulation.
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
        ArrayList<Cell> currNeighbors = this.getNeighbors;
        numAlike = 0;
        for(Cell neighbor = currNeighbors) {
            if(this.getCurrState() == neighbor.getCurrState()) {
                numAlike++;
            }
        }
        if(numAlike > myThreshold*currNeighbors.size()) {
            satisfied = true;
        } else {
            satisfied = false;
        }
    }

    public void updateCell() {
        if(satisfied) {
            this.setPrevState(this.getCurrState());
        } else {
            this.setPrevState(this.getCurrState());
            this.setCurrState(0);
        }
    }

    public int setThreshold(double threshold) {
        myThreshold = threshold;
    }
}
