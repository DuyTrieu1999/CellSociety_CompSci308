package model;

import java.util.ArrayList;

public class Cell {
    private int rowPos;
    private int colPos;
    private String currState;
    private String prevState;
    private String image;
    private ArrayList<Cell> neighbors;

    public Cell(int row, int col) {
        this.rowPos = row;
        this.colPos = col;
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
    public void setCurrState(String currState) {
        this.currState = currState;
    }
    public void setPrevState(String nextState) {
        this.prevState = nextState;
    }
    public String getCurrState() {
        return this.currState;
    }
    public String getPrevState() {
        return this.prevState;
    }
    public Cell updateCell() {
        return this;
    }
}
