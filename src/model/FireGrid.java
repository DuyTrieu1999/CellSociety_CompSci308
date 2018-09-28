package model;

import javafx.scene.paint.Color;
import java.util.*;

public class FireGrid extends Grid {
    public FireGrid(String filename, int size) {
        super(filename, size);
    }
    //For some reason, when testing the simulation, the other method would not work, so I replaced it with this one.
    @Override
    public void storeNeighbors(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();
        if(!outOfBounds(cell.getRowPos()+1, cell.getColPos())) {
            cellNeighbours.add(getGrid()[cell.getRowPos()+1][cell.getColPos()]);
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
    public void fillGrid() {
        if (getCellCounts().size() > 0 && getCellStates().size() > 0 && getCellCounts().size() == getCellStates().size()) {
            TreeMap<Integer, Integer> cellTypeCount = new TreeMap<>();
            for (int k = 0; k < getCellCounts().size(); k++) {
                cellTypeCount.put(k, getCellCounts().get(k));
                //System.out.println(states.get(k));
            }
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    while(true) {
                        int rn = new Random().nextInt(getCellStates().size());
                        if(cellTypeCount.get(rn) > 0) {
                            getGrid()[i][j] = new FireCell(i, j, getMaxGridPaneSize() / this.getColNum());
                            int newCount = cellTypeCount.get(rn) - 1;
                            cellTypeCount.remove(rn);
                            cellTypeCount.put(rn, newCount);
                            StateENUM state = StateENUM.valueOf(getCellStates().get(rn));
                            getGrid()[i][j].setStartState(state);
                            break;
                        }
                    }
                }
            }
        } else {
            System.out.println("Switching to random cell setup");
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    getGrid()[i][j] = new GOLCell(i, j, getMaxGridPaneSize() / this.getColNum());
                    getGrid()[i][j].setRandStartState();
                }
            }
        }
    }
}