package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Grid {
    private Cell[][] grid;
    private Cell cellType;
    private int row;
    private int col;

    public Grid (int r, int c, Cell subCell) {
        row = r;
        col = c;
        cellType = subCell;
        fillGrid();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private void fillGrid() {
        grid = new Cell[row][col];
        for(int i = 0; i < col; i++) {
            for(int j = 0; j < row; j++) {
                grid[i][j] = cellType;
            }
        }
    }
}
