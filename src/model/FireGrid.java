package model;

import javafx.scene.paint.Color;
import java.util.*;

public class FireGrid extends Grid {
    public FireGrid(String simulationName, int size) {
        super(simulationName, size);
    }

    @Override
    public void storeNeighbors(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();
        int[] rowCoord = {cell.getRowPos(), cell.getRowPos()+1, cell.getRowPos()-1};
        int[] colCoord = {cell.getColPos(), cell.getColPos()+1, cell.getColPos()-1};
        for (int row: rowCoord) {
            for (int col: colCoord) {
                if(Math.abs(row - col) == 1) {
                    if (row>-1 && col>-1 && row<(this.getRowNum()) && col<(this.getColNum())) {
                        cellNeighbours.add(this.getGrid()[row][col]);
                    }
                }
            }
        }
        cell.setNeighbors(cellNeighbours);
    }
}