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
    private StateENUM[] standardStates = {StateENUM.ALIVE, StateENUM.DEAD};
    private StateENUM[] hexStates = {StateENUM.YELLOW, StateENUM.ALIVE, StateENUM.RED};

    public GOLCell(int row, int col, double width, String cellType) {
        super(row, col, width, cellType);
    }

    @Override
    public void updateCell() {
        int numAlive = 0;
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        for (Cell neighbor : currNeighbors) {
            if (neighbor.getCurrState() == StateENUM.ALIVE || neighbor.getCurrState() == StateENUM.YELLOW) {
                numAlive++;
            } else if(neighbor.getCurrState() == StateENUM.RED) {
                numAlive += 2;
            }
        }
        if(this.getCellType().equals("Rectangle") || this.getCellType().equals("Triangle")) {
            if (this.getCurrState() == StateENUM.ALIVE && (numAlive < 2 || numAlive > 3)) {
                this.setNextState(StateENUM.DEAD);
            } else if (this.getCurrState() == StateENUM.DEAD && numAlive == 3) {
                this.setNextState(StateENUM.ALIVE);
            } else {
                this.setNextState(this.getCurrState());
            }
            this.setFill(this.getStateColor(this.getNextState()));
        }else if(this.getCellType().equals("Hexagon")) {
            if (this.getCurrState() == StateENUM.DEAD && numAlive == 4) {
                this.setNextState(StateENUM.YELLOW);
            } else if (this.getCurrState() == StateENUM.YELLOW && (numAlive == 6 || numAlive <= 4)) {
                this.setNextState(StateENUM.RED);
            } else if (this.getCurrState() == StateENUM.RED && (numAlive == 4)) {
                this.setNextState(StateENUM.YELLOW);
            } else if (this.getCurrState() == StateENUM.RED && (numAlive == 1 || numAlive == 2)) {
                this.setNextState(this.getCurrState());
            } else {
                this.setNextState(StateENUM.DEAD);
            }
            this.setFill(this.getStateColor(this.getNextState()));
        }
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch(state) {
            case ALIVE:
                return Color.BLACK;
            case DEAD:
                return Color.WHITE;
            case YELLOW:
                return Color.YELLOW;
            case RED:
                return Color.RED;
            default:
                return null;
        }
    }

    @Override
    public void setRandStartState() {
        if(this.getCellType().equals("Rectangle") || this.getCellType().equals("Triangle")) {
            this.setCurrState(standardStates[new Random().nextInt(standardStates.length)]);
            this.setFill(getStateColor(this.getCurrState()));
        }else if(this.getCellType().equals("Hexagon")) {
            this.setCurrState(hexStates[new Random().nextInt(hexStates.length)]);
            this.setFill(getStateColor(this.getCurrState()));
        }


    }
}
