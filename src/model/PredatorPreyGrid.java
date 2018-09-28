package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * This class implements the grid for the Wa-Tor World of Predator Prey Relationships
 * @author Austin Kao, Duy Trieu
 */
public class PredatorPreyGrid extends Grid{
    private final int SHARK_REPRODUCTION_CYCLE_WAIT = 5;
    private final int MAX_SHARK_ENERGY = 3;
    private final int FISH_REPRODUCTION_CYCLE_WAIT = 3;
    private final int ENERGY_FROM_EATING_FISH = 2;

    private HashMap<Integer, Fish> livingFish;
    private HashMap<Integer, Shark> livingSharks;
    private HashMap<Integer, Cell> movedInto;

    public PredatorPreyGrid(String filename, int size) {
        super(filename, size);
        livingFish = new HashMap<>();
        livingSharks = new HashMap<>();
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                if (getGrid()[i][j].getCurrState() == StateENUM.FISH) {
                    livingFish.put(hashCode(i, j), new Fish());
                } else if (getGrid()[i][j].getCurrState() == StateENUM.SHARK) {
                    livingSharks.put(hashCode(i, j), new Shark());
                }
            }
        }
    }

    /**
     * Make updateGrid() conform to the rules of the simulation
     * Sharks move before fish so that the fish that are eaten by sharks don't move.
     */
    @Override
    public void updateGrid() {
        Cell currentCell;
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                getGrid()[i][j].updateCell();
            }
        }
        movedInto = new HashMap<>();
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                currentCell = getGrid()[i][j]; //Technically not needed, but easier to work with
                if (currentCell.getCurrState() == StateENUM.SHARK && currentCell.isMoving()) {
                    ArrayList<Cell> currNeighbors = currentCell.getNeighbors();
                    boolean determinedMove = true;
                    int newHashCode = -1;
                    for(Cell neighbor : currNeighbors) {
                        if(!movedInto.containsKey(hashCode(neighbor.getRowPos(), neighbor.getColPos()))) {
                            determinedMove = false;
                        }
                    }
                    if (!determinedMove) {
                        while (!determinedMove) {
                            int rn = new Random().nextInt(currNeighbors.size());
                            newHashCode = hashCode(currNeighbors.get(rn).getRowPos(), currNeighbors.get(rn).getColPos());
                            if (currentCell.isEating()) {
                                if (currNeighbors.get(rn).getCurrState() == StateENUM.FISH && !movedInto.containsKey(newHashCode)) {
                                    determinedMove = true;
                                    movedInto.put(newHashCode, currNeighbors.get(rn));
                                    livingSharks.get(hashCode(i,j)).updateMovingShark(hashCode(i,j), newHashCode, livingSharks, livingFish);
                                    if(livingSharks.get(newHashCode).hasReproduced()) {
                                        currentCell.setNextState(StateENUM.SHARK);
                                    }
                                    currNeighbors.get(rn).setNextState(StateENUM.SHARK);
                                }
                            } else {
                                if (currNeighbors.get(rn).getCurrState() == StateENUM.WATER && !movedInto.containsKey(newHashCode)) {
                                    determinedMove = true;
                                    movedInto.put(newHashCode, currNeighbors.get(rn));
                                    livingSharks.get(hashCode(i,j)).updateMovingShark(hashCode(i,j), newHashCode, livingSharks, livingFish);
                                    if(livingSharks.get(newHashCode).hasReproduced()) {
                                        currentCell.setNextState(StateENUM.SHARK);
                                    }
                                    currNeighbors.get(rn).setNextState(StateENUM.SHARK);
                                }
                            }
                        }
                    } else {
                        livingSharks.get(hashCode(i,j)).updateUnmovingShark(hashCode(i,j),livingSharks);
                        if(livingSharks.containsKey(hashCode(i,j))) {
                            currentCell.setNextState(StateENUM.SHARK);
                        }
                    }
                } else if (currentCell.getCurrState() == StateENUM.SHARK) {
                    livingSharks.get(hashCode(i, j)).updateUnmovingShark(hashCode(i, j), livingSharks);
                    if(!livingSharks.containsKey(hashCode(i,j))) {
                        currentCell.setNextState(StateENUM.WATER);
                    }
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentCell = getGrid()[i][j];
                if(livingFish.containsKey(hashCode(i,j))) {
                    if (currentCell.getCurrState() == StateENUM.FISH && currentCell.isMoving()) {
                        ArrayList<Cell> currNeighbors = currentCell.getNeighbors();
                        boolean determinedMove = true;
                        for(Cell neighbor : currNeighbors) {
                            if(!movedInto.containsKey(hashCode(neighbor.getRowPos(),neighbor.getColPos()))) {
                                determinedMove = false;
                            }
                        }
                        if (!determinedMove) {
                            while (!determinedMove) {
                                int rn = new Random().nextInt(currNeighbors.size());
                                int newHashCode = hashCode(currNeighbors.get(rn).getRowPos(), currNeighbors.get(rn).getColPos());
                                if (currNeighbors.get(rn).getCurrState() == StateENUM.WATER && !movedInto.containsKey(newHashCode)) {
                                    determinedMove = true;
                                    livingFish.get(hashCode(i, j)).updateMovingFish(hashCode(i,j), newHashCode, livingFish);
                                    currNeighbors.get(rn).setNextState(StateENUM.FISH);
                                    if(livingFish.get(newHashCode).reproduced) {
                                        currentCell.setNextState(StateENUM.FISH);
                                    }
                                }
                            }
                        } else {
                            livingFish.get(hashCode(i,j)).updateUnmovingFish();
                            currentCell.setNextState(StateENUM.FISH);
                        }
                    } else {
                        livingFish.get(hashCode(i,j)).updateUnmovingFish();
                    }
                } else {
                    currentCell.setNextState(StateENUM.WATER);
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentCell = getGrid()[i][j];
                currentCell.setCurrState(currentCell.getNextState());
                currentCell.setFill(currentCell.getStateColor(currentCell.getCurrState()));
            }
        }
        System.out.println(livingFish.size());
        System.out.println(livingSharks.size());
    }

    public int hashCode(int i, int j) {
        return getRowNum()*i+j;
    }
    /**
     * The Fish class is intended to represent a fish in the simulation.
     * Fish have a reproduction time parameter which represents the number of chronons they take to reproduce
     * Rules:
     * At each chronon, fish will move to an adjacent water cell
     * At each chronon, if a fish survives long enough, it will reproduce by leaving a new fish in the cell it leaves behind.
     */
    class Fish {
        private int reproductionTime;
        private boolean reproduced;
        public Fish() {
            this(5);
        }
        public Fish(int reproduction) {
            reproductionTime = reproduction;
            reproduced = false;
        }
        public void updateUnmovingFish() {
            if(reproductionTime > 0) {
                reproductionTime--;
                reproduced = false;
            }
        }
        public void updateMovingFish(int currentHashCode, int newHashCode, HashMap<Integer, Fish> fishMap) {
            fishMap.remove(currentHashCode);
            if(reproductionTime <= 0) {
                reproductionTime = 5;
                fishMap.put(currentHashCode, new Fish());
                reproduced = true;
            } else {
                reproductionTime--;
                reproduced = false;
            }
            fishMap.put(newHashCode, this);
        }
        public boolean hasReproduced() {
            return reproduced;
        }
    }
    /**
     * The Shark class is intended to represent a shark in the simulation
     * Shark have a sharkEnergy parameter which represents the number of chronons they can continue living for without eating fish before dying
     * They also have a reproductionTime parameter which represents the number of chronons they take to reproduce.
     */
    class Shark {
        private int reproductionTime;
        private int sharkEnergy;
        private int maxSharkEnergy;
        private boolean reproduced;
        public Shark() {
            this(6, 6);
        }
        public Shark(int energy, int reproduction) {
            sharkEnergy = energy;
            maxSharkEnergy = energy;
            reproductionTime = reproduction;
        }

        public void updateUnmovingShark(int currentHashCode, HashMap<Integer, Shark> sharkMap) {
            if(sharkEnergy > 0) {
                sharkEnergy--;
                reproduced = false;
                if(reproductionTime > 0) {
                    reproductionTime--;
                }
            } else {
                sharkMap.remove(currentHashCode);
            }
        }

        public void updateMovingShark(int currentHashCode, int newHashCode, HashMap<Integer, Shark> sharkMap, HashMap<Integer, Fish> fishMap) {
            sharkMap.remove(currentHashCode);
            if(fishMap.containsKey(newHashCode)) {
                fishMap.remove(newHashCode);
                sharkEnergy += 2;
                if(sharkEnergy > maxSharkEnergy) {
                    sharkEnergy = maxSharkEnergy;
                }
            } else if(sharkEnergy > 0) {
                sharkEnergy--;
            }
            if(reproductionTime <= 0) {
                reproductionTime = 6;
                sharkMap.put(currentHashCode, new Shark());
                reproduced = true;
            } else {
                reproductionTime--;
                reproduced = false;
            }
            sharkMap.put(newHashCode, this);
        }
        public boolean hasReproduced() {
            return reproduced;
        }
    }

    /**
     * The world of Wa-Tor is a torus, and wraps left to right, top to bottom.
     * This means that any fish or shark that moves right at the rightmost square ends up on the leftmost square in the same row.
     * @param cell is the current cell in question
     */
    @Override
    public void storeNeighbors (Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
        if(cell.getRowPos()<this.getRowNum() - 1) {
            cellNeighbours.add(this.getGrid()[cell.getRowPos()+1][cell.getColPos()]);
        }
        if(cell.getRowPos() > 0) {
            cellNeighbours.add(getGrid()[cell.getRowPos()-1][cell.getColPos()]);
        }
        if(cell.getColPos()<this.getColNum() - 1) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][cell.getColPos()+1]);
        }
        if(cell.getColPos()>0) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][cell.getColPos()-1]);
        }
        if(cell.getRowPos() == this.getRowNum()) {
            cellNeighbours.add(getGrid()[0][cell.getColPos()]);
        }
        if(cell.getRowPos() == 0) {
            cellNeighbours.add(getGrid()[this.getRowNum()-1][cell.getColPos()]);
        }
        if(cell.getRowPos() == this.getColNum()) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][0]);
        }
        if(cell.getRowPos() == 0) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][this.getColNum()-1]);
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
                                    getGrid()[i][j] = new PredatorPreyCell(i, j, getMaxGridPaneSize() / this.getColNum());
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
                    getGrid()[i][j] = new PredatorPreyCell(i, j, getMaxGridPaneSize() / this.getColNum());
                    getGrid()[i][j].setRandStartState();
                }
            }
        }
    }
}
