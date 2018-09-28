package model;

import view.SimulationUI;


import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 *
 * @author duytrieu
 */
public class Grid {
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private static final double MAX_GRID_PANE_SIZE = 360;

    private Cell[][] grid;
    private int size;
    private ResourceBundle myResources;

    public Grid (int size) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Button");
        this.size = size;
        grid = new Cell[size][size];
        fillGrid();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                storeNeighbors(grid[i][j]);
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
                grid[i][j].setCurrState(grid[i][j].getNextState());
            }
        }
    }

    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                grid[i][j] = new GOLCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum());
                grid[i][j].setStartState();
            }
        }
    }

    //May or may not use!
    private Cell[][] chooseSimuGrid (String simuName, int size) {
        if (simuName.equals(myResources.getString("WaTor"))) {
            return new Cell[size][size];
        }
        else if (simuName.equals(myResources.getString("Fire"))) {
            return new FireCell[size][size];
        }
        else if (simuName.equals(myResources.getString("Segg"))) {
            return new SegCell[size][size];
        }
        else {
            return new Cell[size][size];
        }
    }

    public void storeNeighbors (Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();
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

    public int getRowNum () {
        return grid.length;
    }
    public int getColNum () {
        return grid[0].length;
    }
    public Cell getCell (int row, int col) {
        return grid[row][col];
    }
    public Cell[][] getGrid() {return this.grid;}

    public boolean outOfBounds (int row, int col) {
        return (row < 0 || row > getRowNum() || col < 0 || col > getColNum());
    }
<<<<<<< HEAD

=======
    protected double getMaxGridPaneSize() {
        return MAX_GRID_PANE_SIZE;
    }
>>>>>>> 4c376b3e7f4a4f70f531de72b878843fbdd6d265
}
