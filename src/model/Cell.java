package model;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 *
 * @author duytrieu, Austin Kao
 */
public abstract class Cell extends Polygon {
    private int rowPos;
    private int colPos;
    private StateENUM currState;
    private StateENUM nextState;
    private ArrayList<Cell> neighbors;
    private static final double ROOT_THREE_OVER_2 = Math.sqrt(3) / 2;
    private String cellType;
    private ArrayList<StateENUM> cellStateEnums;

    public Cell(int row, int col, double width, String cellType) {
        this.cellType = cellType;
        this.rowPos = row;
        this.colPos = col;
        drawShape(row, col, width);
        this.setStroke(Color.BLACK);
        cellStateEnums = new ArrayList<>();
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
        if (cellType.equals("Rectangle")) {
            drawRectangle(row, col, width);
        }
        if (cellType.equals("Triangle")) {
            drawTriangle(row, col, width);
        }
        if (cellType.equals("Hexagon")) {
            drawHexagon(row, col, width);
        }
    }
    private void drawRectangle (int row, int col, double width) {
        Double[] recPoints = {0.0,0.0,0.0, width,width,width,width,0.0};
        // Set Rectangle Cell
        this.getPoints().addAll(recPoints);
    }
    private void drawTriangle (int row, int col, double width) {
        Double[] trianglePointsUp = {width/2, 0.0, width*3/2, width, -width/2, width};
        Double[] trianglePointsDown = {-width/2, 0.0, width*3/2, 0.0, width/2, width};
        // Set Triangle Cell
        if (col%2 == 1 && row%2 == 1) {
            this.getPoints().addAll(trianglePointsUp);
        }
        if (col%2==1 && row%2 == 0) {
            this.getPoints().addAll(trianglePointsDown);
        }
        if (col % 2 == 0 && row%2 == 1) {
            this.getPoints().addAll(trianglePointsUp);
        }
        if (col % 2 == 0 && row%2 == 0) {
            this.getPoints().addAll(trianglePointsDown);
        }
    }
    private void drawHexagon (int row, int col, double width) {
        Double[] hexagonPoints = hexagonPoints(width / (2*ROOT_THREE_OVER_2));
        int border = 1;
        this.getPoints().addAll(hexagonPoints);
        this.setRotate(90);
        if (col % 2 == 1) {
            this.setTranslateX(width*ROOT_THREE_OVER_2/2+border);
        }
    }
    private Double[] hexagonPoints (double radius) {
        return new Double[]{radius, 0.0,
                radius / 2, ROOT_THREE_OVER_2 * radius,
                -radius / 2, ROOT_THREE_OVER_2 * radius,
                -radius, 0.0,
                -radius / 2, -ROOT_THREE_OVER_2 * radius,
                radius / 2, -ROOT_THREE_OVER_2 * radius};
    }
    public void setRandStartState(){
        int rand = new Random().nextInt(cellStateEnums.size());
        this.setCurrState(cellStateEnums.get(rand));
        this.setFill(getStateColor(this.getCurrState()));
    }
    public abstract void updateCell ();
    public abstract Color getStateColor(StateENUM state);
    public void setHasFish(boolean value) {}
    public void setHasShark(boolean value) {}
    public void setProbCatch(double probability) {}
    public void setThreshold(double threshold) {}
    public void setHasMoved(boolean value) {}
    public void setMoveByAgent1(boolean value) {}
    public void setMoveByAgent2(boolean value) {}
    public ArrayList<StateENUM> getCellStateEnums() {
        return cellStateEnums;
    }
    public String getCellType() {return cellType;}
}

