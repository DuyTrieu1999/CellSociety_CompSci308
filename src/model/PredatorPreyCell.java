package model;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model.
 * States:
 * FISH represents a cell occupied by fish
 * SHARK represents a cell occupied by a shark
 * WATER represents an empty cell in the sea
 * @author Austin Kao, Duy Trieu
 */

public class PredatorPreyCell extends Cell {
    private StateENUM[] states = {StateENUM.FISH, StateENUM.WATER, StateENUM.SHARK};
    private boolean willMove;
    private boolean fishNeighbor;
    private double cellWidth;

    public PredatorPreyCell(int row, int col, double width) {
        super(row, col, width);
        cellWidth = width;
    }
    @Override
    public void updateCell () {
        willMove = false;
        fishNeighbor = false;
        if(this.getCurrState() == StateENUM.WATER) {
            this.setNextState(StateENUM.WATER);
            return;
        }
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        if (this.getCurrState() == StateENUM.SHARK) {
            for(Cell neighbor : currNeighbors) {
                if(neighbor.getCurrState() == StateENUM.FISH) {
                    willMove = true;
                    fishNeighbor = true;
                } else if(neighbor.getCurrState() == StateENUM.WATER) {
                    willMove = true;
                }
            }
            this.setNextState(StateENUM.SHARK);
        } else {
            for(Cell neighbor : currNeighbors) {
                if(neighbor.getCurrState() == StateENUM.WATER) {
                    willMove = true;
                }
            }
            this.setNextState(StateENUM.FISH);
        }
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case FISH:
                return Color.GREEN;
            case SHARK:
                return Color.GRAY;
            case WATER:
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    @Override
    public void setRandStartState() {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
        this.setFill(getStateColor(this.getCurrState()));
    }
    @Override
    public boolean isEating() {
        return fishNeighbor;
    }
    @Override
    public boolean isMoving() {
        return willMove;
    }
}
