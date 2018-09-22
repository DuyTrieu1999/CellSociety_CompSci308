package model;

import java.util.ArrayList;
import java.util.HashMap;
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
    private int currState;
    private int prevState;
    private Map<Integer, Color> stateMap = new HashMap<>();
    private ArrayList<Cell> neighbors;
    private final int DEAD = 0;
    private final int ALIVE = 1;
    private int numAlive;

    public Cell(int row, int col, double width) {
        this.rowPos = row;
        this.colPos = col;
        updateMap();
        this.setFill(Color.WHITE);
        System.out.println(stateMap);
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
    public void updateCell () {
//        prevState = currState;
//        this.setFill(stateMap.get(prevState));
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
        this.setFill(stateMap.get(prevState));
    }
    private void updateMap () {
        stateMap.put(ALIVE, Color.WHITE);
        stateMap.put(DEAD, Color.BLACK);
    }
}
