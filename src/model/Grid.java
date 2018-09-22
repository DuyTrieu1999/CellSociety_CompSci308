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

    public Grid () {
        grid = new Cell[rowLength][colLength];
        fillGrid();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                storeNeighbor(grid[i][j]);
            }
        }
    }

    public void updateCell () {
        for(int i = 0; i < this.getRowNum(); i++) {
            for(int j = 0; j < this.getColNum(); j++) {
                grid[i][j].updateCell();
            }
        }

    }

    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                grid[i][j] = new Cell(i, j, (double)360 / this.getColNum());
            }
        }
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
}
