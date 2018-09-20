package model;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;

public class Grid {

    private Cell[][] grid;
    private int rowLength = 20;
    private int colLength = 20;
    private double xPos;
    private double yPos;

    public Grid () {
        grid = new Cell[rowLength][colLength];
    }
    public void fillGrid (String cellName) {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                Cell temp = returnCell(cellName, i, j);
                grid[i][j] = temp;

                //TODO: Add in rules and stuffs
            }
        }
    }

    public void updateCells() {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                int neighborSum = 0;
                for(int x = -1; x < 1; x++) {
                    for (int y = -1; y < 1; y++) {
                        grid[i + x][j + y].setPrevState(grid[i + x][j + y].getCurrState());
                        neighborSum += grid[i + x][j + y].getCurrState();
                    }
                }
                neighborSum -= grid[i][j].getCurrState();
                if      ((grid[i][j].getCurrState() == 1) && (neighborSum <  2)) grid[i][j].setCurrState(0);
                else if ((grid[i][j].getCurrState() == 1) && (neighborSum >  3)) grid[i][j].setCurrState(0);
                else if ((grid[i][j].getCurrState() == 0) && (neighborSum == 3)) grid[i][j].setCurrState(1);
                else grid[i][j].setCurrState(grid[i][j].getPrevState());
            }

        }
    }
    private boolean rowOutOfBound (int row) {
        return row < 0 || row > getRowNum() - 1;
    }
    private boolean colOutOfBound (int col) {
        return col < 0 || col > getColNum() - 1;
    }
    private int getRowNum () {
        return grid.length;
    }
    private int getColNum () {
        return grid[0].length;
    }
    private void setCell (int row, int col, Cell myCell) {
        grid[row][col] = myCell;
    }
    private Cell getCell (int row, int col) {
        return grid[row][col];
    }

    private Cell returnCell(String cellName, int row, int col) {
        if(cellName.equals("fire"))
            return new FireCell(row,col);
        return null;
    }

}
