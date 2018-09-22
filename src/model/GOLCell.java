package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class implements a cell that would be part of the Game of Life simulation.
 * Most of the initial testing should be with this simulation.
 * States:
 * ALIVE represents a populated area
 * DEAD represents a depopulated area
 * @author Austin Kao
 */

public class GOLCell extends Cell {

    public GOLCell(int row, int col, double width) {
        super(row, col, width);
    }

    @Override
    public void updateCell() {
        int numAlive = 0;
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for (Cell neighbor : currNeighbors) {
            if (neighbor.getCurrState() == StateENUM.ALIVE) {
                numAlive++;
            }
        }
        if (this.getCurrState() == StateENUM.ALIVE && (numAlive < 2)) {
            this.setNextState(StateENUM.DEAD);
        } else if(this.getCurrState() == StateENUM.ALIVE && (numAlive > 3)) {
            this.setNextState(StateENUM.DEAD);
        } else if (this.getCurrState() == StateENUM.DEAD && numAlive == 3) {
            this.setNextState(StateENUM.ALIVE);
        } else if(this.getCurrState() == StateENUM.ALIVE && (numAlive == 2 || numAlive == 3)) {
            this.setNextState(StateENUM.ALIVE);
        }
        this.setFill(this.getStateColor(this.getNextState()));
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case ALIVE:
                return Color.WHITE;
            case DEAD:
                return Color.BLACK;
            default:
                return null;
        }
    }
}
