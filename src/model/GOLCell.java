package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements a cell that would be part of the Game of Life simulation.
 * Most of the initial testing should be with this simulation.
 * States:
 * ALIVE represents a populated area
 * DEAD represents a depopulated area
 * Rules:
 * A live cell surrounded by less than two living neighbors dies.
 * A live cell surrounded by more than three living neighbors dies.
 * A dead cell surrounded by exactly three living neighbors starts living again.
 * A live cell surrounded by two or three living neighbors lives on.
 * @author Austin Kao
 */

public class GOLCell extends Cell {
    private StateENUM[] states = {StateENUM.ALIVE, StateENUM.DEAD};

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
        if (this.getCurrState() == StateENUM.ALIVE && (numAlive < 2 || numAlive > 3)) {
            this.setNextState(StateENUM.DEAD);
        } else if (this.getCurrState() == StateENUM.DEAD && numAlive == 3) {
            this.setNextState(StateENUM.ALIVE);
        } else {
            this.setNextState(this.getCurrState());
        }
        this.setFill(this.getStateColor(this.getNextState()));
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch(state) {
            case ALIVE:
                return Color.BLACK;
            case DEAD:
                return Color.WHITE;
            default:
                return null;
        }
    }

    @Override
    public void setStartState(StateENUM state) {
        this.setCurrState(state);
        this.setFill(getStateColor(state));
    }
    @Override
    public void setRandStartState() {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
        this.setFill(getStateColor(this.getCurrState()));
    }
}
