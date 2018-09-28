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
            int total = 0;
            TreeMap<String, Integer> cellTypeCount = new TreeMap<>();
            for (int k = 0; k < getCellCounts().size(); k++) {
                cellTypeCount.put(getCellStates().get(k), getCellCounts().get(k));
                total += getCellCounts().get(k);
            }
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    boolean createdCell = false;
                    while(!createdCell) {
                        int rn = new Random().nextInt(total);
                        for (String s : cellTypeCount.keySet()) {
                            if(rn > cellTypeCount.get(s)) {
                                int value = cellTypeCount.get(s);
                                rn = rn - value;
                            } else {
                                if(cellTypeCount.get(s) > 0) {
                                    getGrid()[i][j] = new FireCell(i, j, getMaxGridPaneSize() / this.getColNum());
                                    int newCount = cellTypeCount.get(s) - 1;
                                    StateENUM state = StateENUM.valueOf(s);
                                    getGrid()[i][j].setStartState(state);
                                    cellTypeCount.replace(s, newCount);
                                    createdCell = true;
                                    break;
                                }
                            }
                        }
                    }
                    total--;
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