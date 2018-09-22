package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements a cell that would be part of the Game of Life simulation. Most of the initial testing should be with this simulation.
 * @author Austin Kao
 */

public class GOLCell extends Cell {
    private int numAlive;


    public GOLCell(int row, int col, double width) {
        super(row, col, width);
    }

    @Override
    public void updateCell() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for (Cell neighbor : currNeighbors) {
            if (neighbor.getCurrState() == States.ALIVE) {
                numAlive++;
            }
        }
        if (this.getCurrState() == States.ALIVE && (numAlive < 1 || numAlive > 3)) {
            this.setPrevState(States.ALIVE);
            this.setCurrState(States.DEAD);
        } else if (this.getCurrState() == States.DEAD && numAlive == 3) {
            this.setPrevState(States.DEAD);
            this.setCurrState(States.ALIVE);
        }
        this.setFill(States.stateColor(getCurrState()));
    }
}