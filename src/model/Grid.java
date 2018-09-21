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
    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                grid[i][j] = new Cell(i, j, (double)360 / this.getColNum());
                grid[i][j].setFill(Color.BLACK);
            }
        }
    }
    public void updateCell () {

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
