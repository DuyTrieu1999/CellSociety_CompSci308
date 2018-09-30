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
    private static final double DEFAULT_THRESHOLD = 0.25;

    private int numDissatisfied1;
    private int numDissatisfied2;
    private int numDissatisfiedTotal;
    private double threshold;
    private int numVacant;
    private TreeMap<Integer, Cell> vacancies;

    public SegGrid (String filename, int size, String cellType) {
        super(filename, size, cellType);
        double segregationThreshold = determineThreshold();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                getGrid()[i][j].setThreshold(segregationThreshold);
            }
        }
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
        vacancies = new TreeMap<>();
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
        numDissatisfiedTotal = numDissatisfied1 + numDissatisfied2;
        relocateCells(numDissatisfiedTotal, numDissatisfied1, numDissatisfied2);
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                getGrid()[i][j].setCurrState(getGrid()[i][j].getNextState());
            }
        }
    }

    @Override
    public void fillGrid() {
        if(getSaveState().size() > 0) {
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    int index = getRowNum()*i+j;
                    getGrid()[i][j] = new SegCell(i, j, getMaxGridPaneSize() / this.getColNum(), getCellType());
                    getGrid()[i][j].setStartState(StateENUM.valueOf(getSaveState().get(index)));
                }
            }
        } else if (getCellCounts().size() > 0 && getCellStates().size() > 0 && getCellCounts().size() == getCellStates().size()) {
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
                                    getGrid()[i][j] = new SegCell(i, j, getMaxGridPaneSize() / this.getColNum(), this.getCellType());
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
                    getGrid()[i][j] = new SegCell(i, j, getMaxGridPaneSize() / this.getColNum(), this.getCellType());
                    getGrid()[i][j].setRandStartState();
                }
            }
        }
    }

    private void relocateCells(int numTotal, int num1, int num2) {
        while(numTotal > 0) {
            if (vacancies.size() > 0) {
                boolean canMove = false;
                while(!canMove) {
                    int rand = new Random().nextInt(numVacant);
                    if (vacancies.containsKey(rand)) {
                        canMove = true;
                        Cell move = vacancies.get(rand);
                        int rand2 = new Random().nextInt(numTotal);
                        if (rand2 < num1) {
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
                            num1--;
                        } else {
                            num2--;
                        }
                    }
                }
            }
            numTotal = num1 + num2;
        }
    }

    private double determineThreshold() {
        if(getParameterValues().size() > 0) {
            for(String s : getParameterValues().keySet()) {
                if(s.equals("thresholdToCauseSegregation")) {
                    threshold = getParameterValues().get(s);
                }
            }
        } else {
            threshold = DEFAULT_THRESHOLD;
        }
        return threshold;
    }
    public double getThreshold () {
        return this.threshold;
    }
    public void setThreshold (int threshold) {
        this.threshold = threshold;
    }
}

