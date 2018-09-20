package model;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;

public abstract class Grid {

    private Cell[][] grid;
    private int rowLength = 20; //Shouldn't we make these variables part of the constructor?
    private int colLength = 15; //Shouldn't we make these variables part of the constructor?
    private double xPos;
    private double yPos;

    public Grid () {
        grid = new Cell[rowLength][colLength];
    }
    public void fillGrid (Pane pane) {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                Cell cell = grid[i][j];
                //TODO: Add in rules and stuffs
            }
        }
    }
    private boolean rowOutOfBound (int row) {
        return row < 0 || row > getRowNum();
    }
    private boolean colOutOfBound (int col) {
        return col < 0 || col > getColNum();
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
}
