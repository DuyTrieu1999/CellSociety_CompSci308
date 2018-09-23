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
    public void setNeighbors (ArrayList<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public void setCurrState(StateENUM currState) {
        this.currState = currState;
    }
    public void setNextState(StateENUM nextState) {
        this.nextState = nextState;
    }
    public StateENUM getCurrState() {
        return this.currState;
    }
    public StateENUM getNextState() {
        return this.nextState;
    }
    public abstract void setStartState();
    public abstract void updateCell ();
    public abstract Color getStateColor(StateENUM state);
    public boolean isSatisfied() {
        return false;
    }
}

