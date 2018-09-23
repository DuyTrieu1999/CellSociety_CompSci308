package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SegGrid extends Grid{
    private Queue<Cell> emptyCells = new LinkedList<>();;
    private double myThreshold = 0.3;

    public SegGrid(int size) {
        super(size);
    }

    @Override
    public void updateGrid () {
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                Cell cell = this.getGrid()[i][j];
                if(cell.getCurrState() == StateENUM.VACANT) return;

                List<Cell> currNeighbors = cell.getNeighbors();
                int total = 0;
                int occupiedSpots = 0;

                for(Cell neighbour:currNeighbors) {
                    if(neighbour.getCurrState() == cell.getCurrState()) {
                        total++;
                        occupiedSpots++;
                    }else if(neighbour.getCurrState() != StateENUM.VACANT) {
                        occupiedSpots++;
                    }
                }
                if(occupiedSpots == 0) return;
                if((double) total/occupiedSpots < myThreshold * currNeighbors.size()) {
                    moveToVacant(cell);
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                Cell cell = this.getGrid()[i][j];
                cell.setCurrState(cell.getNextState());
            }
        }
    }
    @Override
    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                this.getGrid()[i][j] = new SegCell(i, j, (double)360 / this.getColNum());
                this.getGrid()[i][j].setStartState();
            }
        }
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                if (this.getGrid()[i][j].getCurrState() == StateENUM.VACANT)
                    emptyCells.add(this.getGrid()[i][j]);
                System.out.println(emptyCells);
            }
        }
    }
    public void moveToVacant (Cell cell) {
        Cell newLocation = emptyCells.poll();
        newLocation.setNextState(cell.getCurrState());
        cell.setNextState(StateENUM.VACANT);
        emptyCells.add(cell);
    }
    public void clearValues() {
        emptyCells = new LinkedList<>();
    }
}
