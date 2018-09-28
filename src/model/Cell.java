package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author duytrieu
 */
public abstract class Cell extends Rectangle {
    private int rowPos;
    private int colPos;
    private StateENUM currState;
    private StateENUM nextState;
    private ArrayList<Cell> neighbors;

    public Cell(int row, int col, double width) {
        this.rowPos = row;
        this.colPos = col;
        this.setWidth(width);
        this.setHeight(width);
    }
    public int getRowPos() {
        return rowPos;
    }
    public int getColPos() {
        return colPos;
    }
    public ArrayList<Cell> getNeighbors () {
        return neighbors;
    }
    public void setNeighbors (ArrayList<Cell> neighborList) {
        neighbors = neighborList;
    }

    public void setCurrState(StateENUM newCurrState) {
        currState = newCurrState;
    }
    public void setNextState(StateENUM newNextState) {
        nextState = newNextState;
    }
    public StateENUM getCurrState() {
        return currState;
    }
    public StateENUM getNextState() {
        return nextState;
    }
    public void setStartState(StateENUM state) {
        this.setCurrState(state);
        this.setFill(getStateColor(this.getCurrState()));
    }
    public abstract void setRandStartState();
    public abstract void updateCell ();
    public abstract Color getStateColor(StateENUM state);
    public boolean isSatisfied() {
        return false;
    }
    public PredatorPreyCell getMove() {
        return null;
    }
    public boolean isMoving() {
        return false;
    }
    public boolean isEating() {
        return false;
    }
    public void setSatisfaction(boolean value) {}
    public void setProbCatch(double probability) {}
}

