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

    private Cell[][] grid;
    private int rowLength = 20;
    private int colLength = 20;
    private double xPos;
    private double yPos;
    private double thresholdValue; //for Fire, Segregation models?
    private int numCells; //for Segregation model?
    private String simulationName;

    public Grid (String simulationName) {
        this.simulationName = simulationName;
        grid = new Cell[rowLength][colLength];
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
                Cell cell = grid[i][j];
                cell.setCurrState(cell.getNextState());
            }
        }
    }

    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                grid[i][j] = chooseSimuCell(simulationName, i, j, (double)360 / this.getColNum());
                grid[i][j].setStartState();
            }
        }
    }
    public Cell chooseSimuCell (String simuName, int i, int j, double width) {
        if (simuName.equals("Game of Life"))
            return new GOLCell(i, j, width);
        else if (simuName.equals("Wa-Tor World model"))
            return new PredatorPreyCell(i, j, width);
        else if (simuName.equals("Spreading of Fire"))
            return new FireCell(i, j, width);
        else
            return new SegCell(i, j, width);
    }

    public void storeNeighbors (Cell cell) {
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

    public boolean outOfBounds (int row, int col) {
        return (row < 0 || row > getRowNum() || col < 0 || col > getColNum());
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
    public Cell[][] getGrid() {
        return grid;
    }
}
