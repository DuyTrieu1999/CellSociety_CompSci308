package model;

import java.util.ArrayList;

public class FireCellGrid extends Grid{
    public FireCellGrid(int size) {
        super(size);
    }

    @Override
    public void storeNeighbor(Cell cell) {
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
    @Override
    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                this.getGrid()[i][j] = new FireCell(i, j, (double)360 / this.getColNum());
                this.getGrid()[i][j].setStartState();
            }
        }
    }
}
