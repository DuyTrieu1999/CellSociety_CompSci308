package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class SegGrid extends Grid {
    SegCell[][] grid;
    private int numDisatisfied1;
    private int numDisatisfied2;
    private TreeMap<Integer, SegCell> vacancies;
    private int numVacant;

    public SegGrid (String simulationName, int size) {
        super(simulationName, size);
    }

    @Override
    public void updateGrid () {
        numVacant = 0;
        boolean currentlySatisfied;
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentlySatisfied = grid[i][j].isSatisfied();
                if(!currentlySatisfied && (grid[i][j].getCurrState() == StateENUM.AGENT1)) {
                    numDisatisfied1++;
                } else if(!currentlySatisfied && (grid[i][j].getCurrState() == StateENUM.AGENT2)) {
                    numDisatisfied2++;
                } else if(getGrid()[i][j].getCurrState() == StateENUM.VACANT) {
                    vacancies.put(numVacant, grid[i][j]);
                    numVacant++;
                }
                getGrid()[i][j].updateCell();
            }
        }
        for (int k=0; k<numDisatisfied1; k++) {
            currentlySatisfied = false;
            while (!currentlySatisfied) {
                int rand = new Random().nextInt(vacancies.size());
                SegCell move = vacancies.get(rand);
                move.setCurrState(StateENUM.AGENT1);
                if(move.isSatisfied()) {
                    currentlySatisfied = true;
                    move.updateCell();
                } else {
                    move.setCurrState(StateENUM.VACANT);
                }
            }
        }
        for(int l = 0; l < numDisatisfied2; l++) {
            currentlySatisfied = false;
            while (!currentlySatisfied) {
                int rand = new Random().nextInt(vacancies.size());
                SegCell move = vacancies.get(rand);
                move.setCurrState(StateENUM.AGENT2);
                if(move.isSatisfied()) {
                    currentlySatisfied = true;
                    move.updateCell();
                } else {
                    move.setCurrState(StateENUM.VACANT);
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                grid[i][j].setCurrState(grid[i][j].getNextState());
            }
        }
    }
}
