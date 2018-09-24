package model;

import java.util.ArrayList;

public class PredatorPreyGrid extends Grid{
    public PredatorPreyGrid(String stringname, int size) {
        super(stringname, size);
    }

    @Override
    //For models that only act on the cells left, right, up, down
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
}

