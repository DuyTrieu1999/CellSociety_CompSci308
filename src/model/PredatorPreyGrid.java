package model;

import java.util.ArrayList;

public class PredatorPreyGrid extends Grid {
    public PredatorPreyGrid(int size) {
        super(size);
    }
    @Override
    public void storeNeighbor (Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
        if(!outOfBounds(cell.getRowPos()+1, cell.getColPos())) {
            cellNeighbours.add(this.getGrid()[cell.getRowPos()+1][cell.getColPos()]);
        }
        if(!outOfBounds(cell.getRowPos()-1, cell.getColPos())) {
            cellNeighbours.add(getGrid()[cell.getRowPos()-1][cell.getColPos()]);
        }
        if(!outOfBounds(cell.getRowPos(), cell.getColPos()+1)) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][cell.getColPos()+1]);
        }
        if(!outOfBounds(cell.getRowPos(), cell.getColPos()-1)) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][cell.getColPos()-1]);
        }
        cell.setNeighbors(cellNeighbours);
    }
    @Override
    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                this.getGrid()[i][j] = new PredatorPreyCell(i, j, (double)360 / this.getColNum());
                this.getGrid()[i][j].setStartState();
            }
        }
    }
}
