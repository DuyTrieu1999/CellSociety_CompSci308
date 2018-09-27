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
    private int numDissatisfiedTotal;
    private int numVacant;

    public SegGrid (int size) {
        super(size);
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
        TreeMap<Integer, Cell> vacancies = new TreeMap<Integer, Cell>();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentlySatisfied = getGrid()[i][j].isSatisfied();
                if(!currentlySatisfied && (getGrid()[i][j].getCurrState() == StateENUM.AGENT1)) {
                    numDissatisfied1++;
                } else if(!currentlySatisfied && (getGrid()[i][j].getCurrState() == StateENUM.AGENT2)) {
                    numDissatisfied2++;
                }
                getGrid()[i][j].updateCell();
                if(getGrid()[i][j].getCurrState() == StateENUM.VACANT) {
                    vacancies.put(numVacant, getGrid()[i][j]);
                    numVacant++;
                }
            }
        }
        //Need to change this; agents should move as one (oops)
        numDissatisfiedTotal = numDissatisfied1 + numDissatisfied2;
        while(numDissatisfiedTotal > 0) {
            if (vacancies.size() > 0) {
                boolean canMove = false;
                while(!canMove) {
                    int rand = new Random().nextInt(numVacant);
                    if (vacancies.containsKey(rand)) {
                        canMove = true;
                        Cell move = vacancies.get(rand);
                        int rand2 = new Random().nextInt(numDissatisfiedTotal);
                        if (rand2 < numDissatisfied1) {
                            move.setCurrState(StateENUM.AGENT1);
                            move.setNextState(StateENUM.AGENT1);
                        } else {
                            move.setCurrState(StateENUM.AGENT2);
                            move.setNextState(StateENUM.AGENT2);
                        }
                        move.setSatisfaction(true);
                        move.updateCell();
                        vacancies.remove(rand);
                        if (move.getCurrState() == StateENUM.AGENT1) {
                            numDissatisfied1--;
                        } else {
                            numDissatisfied2--;
                        }
                    }
                }
            }
            numDissatisfiedTotal = numDissatisfied1 + numDissatisfied2;
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                getGrid()[i][j].setCurrState(getGrid()[i][j].getNextState());
            }
        }
    }

    @Override
    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                this.getGrid()[i][j] = new SegCell(i, j, getMaxGridPaneSize() / this.getColNum());
                this.getGrid()[i][j].setStartState();
            }
        }
    }
}
