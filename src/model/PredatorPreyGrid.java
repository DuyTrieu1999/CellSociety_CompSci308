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
        for(int hashCode : livingFish.keySet()) {
            int row = hashCode / getRowNum();
            int col = Math.floorMod(hashCode, getRowNum());
            int currentHashCode = row * getRowNum() + col;
            Fish currentFish = livingFish.get(hashCode);
            currentCell = getGrid()[row][col];
            ArrayList<Cell> neighborList = currentCell.getNeighbors();
            boolean canMove = false;
            for(Cell neighbor : neighborList) {
                int neighborHashCode = hashCode(neighbor.getRowPos(), neighbor.getColPos());
                if(!livingSharks.containsKey(neighborHashCode) && !livingFish.containsKey(neighborHashCode)) {
                    canMove = true;
                }
            }
            if (canMove) {
                boolean determinedMove = false;
                while (!determinedMove) {
                    int rn = new Random().nextInt(neighborList.size());
                    int newHashCode = hashCode(neighborList.get(rn).getRowPos(), neighborList.get(rn).getColPos());
                    if(!livingSharks.containsKey(newHashCode) && !livingFish.containsKey(newHashCode)) {
                        determinedMove = true;
                        currentFish.updateMovingFish(currentHashCode, newHashCode, livingFish);
                        neighborList.get(rn).setHasFish(true);
                    }
                }
            } else {
                currentFish.updateUnmovingFish();
                currentCell.setHasFish(true);
            }
        }
        for(int hashCode : livingSharks.keySet()) {
            int currentHashCode = hashCode;
            int row = hashCode / getRowNum();
            int col = Math.floorMod(hashCode, getRowNum());
            Shark currentShark = livingSharks.get(hashCode);
            currentCell = getGrid()[row][col];
            ArrayList<Cell> neighborList = currentCell.getNeighbors();
            boolean canMove = false;
            boolean canEat = false;
            for(Cell neighbor : neighborList) {
                int neighborHashCode = hashCode(neighbor.getRowPos(), neighbor.getColPos());
                if(livingFish.containsKey(neighborHashCode)) {
                    canMove = true;
                    canEat = true;
                } else if (!livingSharks.containsKey(neighborHashCode)) {
                    canMove = true;
                }
            }
            if (canMove) {
                boolean determinedMove = false;
                while (!determinedMove) {
                    int rn = new Random().nextInt(neighborList.size());
                    int newHashCode = hashCode(neighborList.get(rn).getRowPos(), neighborList.get(rn).getColPos());
                    if(canEat) {
                        if(livingFish.containsKey(newHashCode)) {
                            determinedMove = true;
                            currentShark.updateMovingShark(currentHashCode, newHashCode, livingSharks, livingFish);
                            neighborList.get(rn).setHasShark(true);
                            neighborList.get(rn).setHasFish(false);
                        }
                    } else if(!livingSharks.containsKey(newHashCode)) {
                        determinedMove = true;
                        currentShark.updateMovingShark(currentHashCode, newHashCode, livingSharks, livingFish);
                        neighborList.get(rn).setHasShark(true);
                    }
                }
            } else {
                currentShark.updateUnmovingShark(currentHashCode, livingSharks);
                if(livingSharks.containsKey(currentHashCode)) {
                    currentCell.setHasShark(true);
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentCell = getGrid()[i][j];
                currentCell.updateCell();
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
