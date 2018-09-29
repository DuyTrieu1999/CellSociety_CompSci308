package model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 *
 * @author duytrieu
 */
public abstract class Cell extends Polygon {
    private int rowPos;
    private int colPos;
    private StateENUM currState;
    private StateENUM nextState;
    private ArrayList<Cell> neighbors;

    public Cell(int row, int col, double width) {
        this.rowPos = row;
        this.colPos = col;
        drawShape(row, col, width);
//        this.setWidth(width);
//        this.setHeight(width);
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
    public void drawShape (int row, int col, double width) {
        Double[] recPoints = {0.0,0.0,0.0, width,width,width,width,0.0};
        Double[] trianglePointsUp = {width/2, 0.0, width*3/2, width, -width/2, width};
        Double[] trianglePointsDown = {-width/2, 0.0, width*3/2, 0.0, width/2, width};
        Double[] hexagonPointsUp = {width/2-width/(2*Math.sqrt(3)),0.0,
                width/2+width/(2*Math.sqrt(3)),0.0,
                width/2+width/Math.sqrt(3),width/2,
                width/2+width/(2*Math.sqrt(3)),width,
                width/2-width/(2*Math.sqrt(3)),width,
                width/2-width/(Math.sqrt(3)),width/2};
        Double[] hexagonPointsDown = {width/2-width/(2*Math.sqrt(3)),width/2,
                width/2+width/(2*Math.sqrt(3)), width/2,
                width/2+width/Math.sqrt(3), width,
                width/2-width/(2*Math.sqrt(3)), width*3/2,
                width/2-width/(2*Math.sqrt(3)), width*3/2,
                width/2-width/(Math.sqrt(3)), width};
//        if ((col+row) % 2 == 1) {
//            this.getPoints().addAll(trianglePointsDown);
//        }
//        if (col % 2 == 0) {
//            this.getPoints().addAll(trianglePointsUp);
//        }
        this.getPoints().addAll(recPoints);
    }
    public abstract void setRandStartState();
    public abstract void updateCell ();
    public abstract Color getStateColor(StateENUM state);
    public boolean isSatisfied() {
        return false;
    }
    public void setHasFish(boolean value) {}
    public void setHasShark(boolean value) {}
    public void setSatisfaction(boolean value) {}
    public void setProbCatch(double probability) {}
    public void setThreshold(double threshold) {}
}

