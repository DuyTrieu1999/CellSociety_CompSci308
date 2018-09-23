package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * This class represents the grid for the Segregation model.
 * There are two types of agents, 1 and 2, in this simulation.
 * Based on whether or not a certain percentage of their neighbors are the same type of agent, each cell will choose to move or not to move its location.
 * Each agent that decides to move will relocate somewhere in the Grid where they are satisfied with the location.
 * All dissatisfied agents move at the same time.
 * @author Austin Kao
 */
public class SegGrid extends Grid {
    private int numDissatisfied1;
    private int numDissatisfied2;
    private TreeMap<Integer, Cell> vacancies;
    private int numVacant;

    public SegGrid (String simulationName, int size) {
        super(simulationName, size);
    }

    @Override
    public void updateGrid () {
        /**
         * @param numDissatisfied1 is the number of dissatisfied Agent 1 types
         * @param numDissatisfied2 is the number of dissatisfied Agent 2 types
         * @param numVacant is the number of vacant cells
         * @param currentlySatisfied keeps track of whether the current cell is satisfied with its location or not
         */
        numDissatisfied1 = 0;
        numDissatisfied2 = 0;
        numVacant = 0;
        boolean currentlySatisfied;
        vacancies = new TreeMap<Integer, Cell>();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentlySatisfied = getGrid()[i][j].isSatisfied();
                if(!currentlySatisfied && (getGrid()[i][j].getCurrState() == StateENUM.AGENT1)) {
                    numDissatisfied1++;
                } else if(!currentlySatisfied && (getGrid()[i][j].getCurrState() == StateENUM.AGENT2)) {
                    numDissatisfied2++;
                } else if(getGrid()[i][j].getCurrState() == StateENUM.VACANT) {
                    vacancies.put(numVacant, getGrid()[i][j]);
                    numVacant++;
                }
                getGrid()[i][j].updateCell();
            }
        }
        for (int k = 0; k < numDissatisfied1; k++) {
            currentlySatisfied = false;
            while (!currentlySatisfied) {
                if(vacancies.size() > 0) {
                    int rand = new Random().nextInt(vacancies.size());
                    Cell move = vacancies.get(rand);
                    if(move.getCurrState() == StateENUM.VACANT) {
                        move.setCurrState(StateENUM.AGENT1);
                        if (move.isSatisfied()) {
                            currentlySatisfied = true;
                            move.updateCell();
                        } else {
                            move.setCurrState(StateENUM.VACANT);
                        }
                    }
                } else {
                    currentlySatisfied = true;
                }
            }
        }
        for(int l = 0; l < numDissatisfied2; l++) {
            currentlySatisfied = false;
            while (!currentlySatisfied) {
                if(vacancies.size() > 0) {
                    int rand = new Random().nextInt(vacancies.size());
                    Cell move = vacancies.get(rand);
                    move.setCurrState(StateENUM.AGENT2);
                    if (move.isSatisfied()) {
                        currentlySatisfied = true;
                        move.updateCell();
                    } else {
                        move.setCurrState(StateENUM.VACANT);
                    }
                } else {
                    currentlySatisfied = true;
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                getGrid()[i][j].setCurrState(getGrid()[i][j].getNextState());
            }
        }
    }
}
