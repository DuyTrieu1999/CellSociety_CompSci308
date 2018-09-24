package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/**
 *
 * @author duytrieu
 */
public class Grid {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    private Cell[][] grid;
    private int size;
    private double xPos;
    private double yPos;
    private double thresholdValue; //for Fire, Segregation models?
    private int numCells; //for Segregation model?
    private String simulationName;
    private ResourceBundle myResources;

    public Grid (String simulationName, int size) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Button");
        this.size = size;
        this.simulationName = simulationName;
        grid = new Cell[size][size];
        fillGrid();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                storeNeighbor(grid[i][j]);
            }
        }
    }
    public void updateGrid () {
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                grid[i][j].updateCell();
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                Cell cell = grid[i][j];
                cell.setCurrState(cell.getNextState());
            }
        }
    }

    private void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                grid[i][j] = chooseSimuCell(simulationName, i, j, (double)360 / this.getColNum());
                grid[i][j].setStartState();
            }
        }
    }
    private Cell chooseSimuCell (String simuName, int i, int j, double width) {
        if (simuName.equals(myResources.getString("GOL")))
            return new GOLCell(i, j, width);
        else if (simuName.equals(myResources.getString("WaTor")))
            return new PredatorPreyCell(i, j, width);
        else if (simuName.equals(myResources.getString("Fire")))
            return new FireCell(i, j, width);
        else if (simuName.equals(myResources.getString("Segg")))
            return new SegCell(i, j, width);
        else
            return null;
    }

    public void storeNeighbor (Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
        int[] rowCoord = {cell.getRowPos(), cell.getRowPos()+1, cell.getRowPos()-1};
        int[] colCoord = {cell.getColPos(), cell.getColPos()+1, cell.getColPos()-1};
        for (int row: rowCoord) {
            for (int col: colCoord) {
                if (row>-1 && col>-1 && row<(this.getRowNum()) && col<(this.getColNum())) {
                    cellNeighbours.add(grid[row][col]);
                }
            }
        }
        cellNeighbours.remove(grid[cell.getRowPos()][cell.getColPos()]);
        cell.setNeighbors(cellNeighbours);

    }
    private boolean rowOutOfBound (int row) {
        return row < 0 || row > getRowNum();
    }
    private boolean colOutOfBound (int col) {
        return col < 0 || col > getColNum();
    }
    public int getRowNum () {
        return grid.length;
    }
    public int getColNum () {
        return grid[0].length;
    }
    public void setCell (int row, int col, Cell myCell) {
        grid[row][col] = myCell;
    }
    public Cell getCell (int row, int col) {
        return grid[row][col];
    }
    public int returnCol () {
        return this.size;
    }
    public Cell[][] getGrid() {return this.grid;}

    public boolean outOfBounds (int row, int col) {
        return (row < 0 || row > getRowNum() || col < 0 || col > getColNum());
    }

}
