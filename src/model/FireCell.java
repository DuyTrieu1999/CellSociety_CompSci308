package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * This class implements the Spreading by Fire simulation.
 * @author Austin Kao
 */

public class FireCell extends Cell {
    private double probCatch;
    private boolean hasNeighborFire;

    public FireCell (int row, int col, double width) {
        super(row, col, width);
        hasNeighborFire = false;
    }

    public void updateCell() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for(Cell neighbor : currNeighbors) {
            if(neighbor.getCurrState() == 2) {
                hasNeighborFire = true;
            }
        }
        if(hasNeighborFire && this.getCurrState() == 1) {
            double rn = Math.random();
            this.setPrevState(1);
            if(rn < probCatch) {
                this.setCurrState(2);
            }
        }
    }

    public void setProbCatch(double probability) {
        probCatch = probability;
    }
}
