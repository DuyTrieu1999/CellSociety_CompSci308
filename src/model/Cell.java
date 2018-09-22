package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author duytrieu
 */
public class Cell extends Rectangle{
    private int rowPos;
    private int colPos;
    private States currState;
    private States prevState;
    private ArrayList<Cell> neighbors;

    public Cell(int row, int col, double width) {
        this.rowPos = row;
        this.colPos = col;
        //this.setFill(stateMap.get(prevState));
        this.setFill(Color.BLACK);
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
    public void setCurrState(States currState) {
        this.currState = currState;
    }
    public void setPrevState(States nextState) {
        this.prevState = nextState;
    }
    public States getCurrState() {
        return this.currState;
    }
    public States getPrevState() {
        return this.prevState;
    }
    public void updateCell () {

    }
}
