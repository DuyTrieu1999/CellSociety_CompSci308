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
    }

    public Cell[][] fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                //Temporary; replace when XML files are created
                Cell temp = new Cell(i,j);
                grid[i][j] = temp;
                //TODO: Add in rules and stuffs
            }
        }
    }

    public void updateCells() {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                int neighborSum = 0;
                List<Cell> mi = grid[i][j].getNeighbors();
                for(Cell m: mi) {
                    neighborSum += m.getPrevState();
                }
//                for(int x = -1; x < 1; x++) {
//                    for (int y = -1; y < 1; y++) {
//                        grid[i + x][j + y].setPrevState(grid[i + x][j + y].getCurrState());
//                        neighborSum += grid[i + x][j + y].getCurrState();
//                    }
//                }
                neighborSum -= grid[i][j].getCurrState();
                if      ((grid[i][j].getCurrState() == 1) && (neighborSum <  2)) grid[i][j].setCurrState(0);
                else if ((grid[i][j].getCurrState() == 1) && (neighborSum >  3)) grid[i][j].setCurrState(0);
                else if ((grid[i][j].getCurrState() == 0) && (neighborSum == 3)) grid[i][j].setCurrState(1);
                else grid[i][j].setCurrState(grid[i][j].getPrevState());
            }
        }
    }
    //Merged rowOutOfBounds and colOutOfBounds
    private boolean outOfBounds (int row, int col) {
        return row < 0 || row > getRowNum() - 1 || col < 0 || col > getColNum() - 1;
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

    private Cell returnCell(String cellName, int row, int col) {
        if(cellName.equals("fire"))
            return new FireCell(row,col);
        return null;
    }

}
