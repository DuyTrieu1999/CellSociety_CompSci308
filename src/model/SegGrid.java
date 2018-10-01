package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * This class represents the grid for the Segregation model.
 * There are two types of agents, 1 and 2, in this simulation.
 * Based on whether or not a certain percentage of their neighbors are the same type of agent, each cell will choose to move or not to move its location.
 * Each agent that decides to move will relocate to a vacant cell somewhere in the Grid.
 * All dissatisfied agents move at the same time.
 * @author Austin Kao
 */
public class SegGrid extends Grid {
    private static final double DEFAULT_THRESHOLD = 0.25;

    private double threshold;
    private int numVacant;
    private TreeMap<Integer, Cell> vacancies;
    private ArrayList<Integer> dissatisfiedAgent1Index;
    private ArrayList<Integer> dissatisfiedAgent2Index;

    public SegGrid (String filename, int size, String cellType) {
        super(filename, size, cellType);
        threshold = determineThreshold();
        if(threshold <= 0) {
            threshold = DEFAULT_THRESHOLD;
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
        int numDissatisfied1 = 0;
        int numDissatisfied2 = 0;
        numVacant = 0;
        boolean currentlySatisfied;
        vacancies = new TreeMap<>();
        dissatisfiedAgent1Index = new ArrayList<>();
        dissatisfiedAgent2Index = new ArrayList<>();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentlySatisfied = determineSatisfaction(getGrid()[i][j]);
                if(!currentlySatisfied && (getGrid()[i][j].getCurrState() == StateENUM.AGENT1)) {
                    numDissatisfied1++;
                    getGrid()[i][j].setHasMoved(true);
                    dissatisfiedAgent1Index.add(getRowNum()*i+j);
                } else if(!currentlySatisfied && (getGrid()[i][j].getCurrState() == StateENUM.AGENT2)) {
                    numDissatisfied2++;
                    getGrid()[i][j].setHasMoved(true);
                    dissatisfiedAgent2Index.add(getRowNum()*i+j);
                }
                if(getGrid()[i][j].getCurrState() == StateENUM.VACANT) {
                    vacancies.put(numVacant, getGrid()[i][j]);
                    numVacant++;
                }
            }
        }
        int numDissatisfiedTotal = numDissatisfied1 + numDissatisfied2;
        relocateCells(numDissatisfiedTotal, numDissatisfied1, numDissatisfied2);
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                getGrid()[i][j].updateCell();
                getGrid()[i][j].setCurrState(getGrid()[i][j].getNextState());
            }
        }
    }

    private void relocateCells(int numTotal, int num1, int num2) {
        while (vacancies.size() > 0 && numTotal > 0) {
            boolean canMove = false;
            while (!canMove) {
                int rand = new Random().nextInt(numVacant);
                if (vacancies.containsKey(rand)) {
                    canMove = true;
                    Cell move = vacancies.get(rand);
                    int rand2 = new Random().nextInt(numTotal);
                    if (rand2 < num1) {
                        move.setMoveByAgent1(true);
                        num1--;
                    } else {
                        move.setMoveByAgent2(true);
                        num2--;
                    }
                    vacancies.remove(rand);
                }
            }
            numTotal = num1 + num2;
        }
        setCellsThatDontMove(num1, dissatisfiedAgent1Index);
        setCellsThatDontMove(num2, dissatisfiedAgent2Index);
    }

    private void setCellsThatDontMove(int number, ArrayList<Integer> dissatisfiedIndex) {
        while(number > 0) {
            int rn = new Random().nextInt(dissatisfiedIndex.size());
            int row = dissatisfiedIndex.get(rn) / getRowNum();
            int col = Math.floorMod(dissatisfiedIndex.get(rn), getRowNum());
            getGrid()[row][col].setHasMoved(false);
            dissatisfiedIndex.remove(rn);
            number--;
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

    private boolean determineSatisfaction(Cell cell) {
        ArrayList<Cell> neighborList = cell.getNeighbors();
        double numAlike = 0;
        double agentNeighbors = 0;
        for(Cell neighbor : neighborList) {
            if(neighbor.getCurrState() == StateENUM.AGENT1 || neighbor.getCurrState() == StateENUM.AGENT2) {
                agentNeighbors++;
            }
            if(cell.getCurrState() == neighbor.getCurrState()) {
                numAlike++;
            }
        }
        return agentNeighbors == 0 || threshold <= numAlike/agentNeighbors;
    }

    public double getThreshold () {
        return this.threshold;
    }
    public void setThreshold (int threshold) {
        this.threshold = threshold;
    }
}

