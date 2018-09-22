package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements a cell that would be part of the Game of Life simulation.
 * Most of the initial testing should be with this simulation.
 * @author Austin Kao
 */

public class GOLCell extends Cell {
    private final int DEAD = 0;
    private final int ALIVE = 1;
    private int numAlive;

    public GOLCell(int row, int col, double width) {
        super(row, col, width);
    }

    @Override
    public void updateCell() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for (Cell neighbor : currNeighbors) {
            if (neighbor.getCurrState() == ALIVE) {
                numAlive++;
            }
        }
        if (this.getCurrState() == 1 && (numAlive < 1 || numAlive > 3)) {
            this.setPrevState(ALIVE);
            this.setCurrState(DEAD);
        } else if (this.getCurrState() == 0 && numAlive == 3) {
            this.setPrevState(DEAD);
            this.setCurrState(ALIVE);
        }
    }
}