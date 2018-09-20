package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle{
    private int rowPos;
    private int colPos;
    private int currState;
    private int prevState;
    private Map<Integer, Color> stateMap;
    private ArrayList<Cell> neighbors;

    public Cell(int row, int col) {
        this.rowPos = row;
        this.colPos = col;
        //this.setFill(stateMap.get(currState));
        this.setFill(Color.BLACK);
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
    public void setCurrState(int currState) {
        this.currState = currState;
    }
    public void setPrevState(int nextState) {
        this.prevState = nextState;
    }
    public int getCurrState() {
        return this.currState;
    }
    public int getPrevState() {
        return this.prevState;
    }
    public void updateState () {
        prevState = currState;
    }
    public void fillMap () {

    }
}
