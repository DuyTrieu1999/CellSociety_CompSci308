package model;

import java.util.*;

/**
 * This class implements the grid for the Wa-Tor World of Predator Prey Relationships
 * @author Austin Kao, Duy Trieu
 */
public class PredatorPreyGrid extends Grid{
    private final double DEFAULT_SHARK_REPRODUCTION_TIME = 6;
    private final double DEFAULT_MAX_SHARK_ENERGY = 2;
    private final double DEFAULT_ENERGY_FROM_EATING_FISH = 2;
    private final double DEFAULT_FISH_REPRODUCTION_TIME = 2;
    private double fishReproductionTime;
    private double sharkReproductionTime;
    private double maxSharkEnergy;
    private double energyGainedFromFish;

    private HashMap<Integer, Fish> livingFish;
    private HashMap<Integer, Shark> livingSharks;

    public PredatorPreyGrid(String filename, int size, String cellType) {
        super(filename, size, cellType);
        livingFish = new HashMap<>();
        livingSharks = new HashMap<>();
        determineParameters();
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                if (getGrid()[i][j].getCurrState() == StateENUM.FISH) {
                    livingFish.put(hashCode(i, j), new Fish(fishReproductionTime));
                } else if (getGrid()[i][j].getCurrState() == StateENUM.SHARK) {
                    livingSharks.put(hashCode(i, j), new Shark(maxSharkEnergy, sharkReproductionTime));
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
        Set<Integer> currentLivingFish = new HashSet<>(livingFish.keySet());
        for(int hashCode : currentLivingFish) {
            int row = hashCode / getRowNum();
            int col = Math.floorMod(hashCode, getRowNum());
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
                        currentFish.updateMovingFish(hashCode, newHashCode, livingFish);
                        neighborList.get(rn).setHasFish(true);
                        if(livingFish.get(newHashCode).hasReproduced()) {
                            currentCell.setHasFish(true);
                        }
                    }
                }
            } else {
                currentFish.updateUnmovingFish();
                currentCell.setHasFish(true);
            }
        }
        Set<Integer> currentLivingSharks = new HashSet<>(livingSharks.keySet());
        for(int hashCode : currentLivingSharks) {
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
                            currentShark.updateMovingShark(hashCode, newHashCode, livingSharks, livingFish);
                            neighborList.get(rn).setHasShark(true);
                            neighborList.get(rn).setHasFish(false);
                            if(livingSharks.get(newHashCode).hasReproduced()) {
                                currentCell.setHasShark(true);
                            }
                        }
                    } else if(!livingSharks.containsKey(newHashCode)) {
                        determinedMove = true;
                        currentShark.updateMovingShark(hashCode, newHashCode, livingSharks, livingFish);
                        if(livingSharks.containsKey(newHashCode)) {
                            neighborList.get(rn).setHasShark(true);
                            if(livingSharks.get(newHashCode).hasReproduced()) {
                                currentCell.setHasShark(true);
                            }
                        }
                    }
                }
            } else {
                currentShark.updateUnmovingShark(hashCode, livingSharks);
                if(livingSharks.containsKey(hashCode)) {
                    currentCell.setHasShark(true);
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                currentCell = getGrid()[i][j];
                currentCell.updateCell();
                currentCell.setCurrState(currentCell.getNextState());
            }
        }
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
        private double reproductionTime;
        private boolean reproduced;

        public Fish(double reproduction) {
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
                reproductionTime = fishReproductionTime;
                fishMap.put(currentHashCode, new Fish(fishReproductionTime));
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
        private double reproductionTime;
        private double sharkEnergy;
        private double maxSharkEnergy;
        private boolean reproduced;

        public Shark(double energy, double reproduction) {
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
                sharkEnergy += energyGainedFromFish;
                if(sharkEnergy > maxSharkEnergy) {
                    sharkEnergy = maxSharkEnergy;
                }
                reproduceIfPossible(currentHashCode, sharkMap);
                sharkMap.put(newHashCode, this);
            } else if(sharkEnergy > 0) {
                sharkEnergy--;
                reproduceIfPossible(currentHashCode, sharkMap);
                sharkMap.put(newHashCode, this);
            } else {
                sharkMap.remove(currentHashCode);
            }
        }
        public boolean hasReproduced() {
            return reproduced;
        }

        private void reproduceIfPossible(int currentHashCode, HashMap<Integer, Shark> sharkMap) {
            if(reproductionTime <= 0) {
                reproductionTime = sharkReproductionTime;
                sharkMap.put(currentHashCode, new Shark(maxSharkEnergy, sharkReproductionTime));
                reproduced = true;
            } else {
                reproductionTime--;
                reproduced = false;
            }
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
        if(getSaveState().size() > 0) {
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    int index = getRowNum()*i+j;
                    getGrid()[i][j] = new PredatorPreyCell(i, j, getMaxGridPaneSize() / this.getColNum(), getCellType());
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
                                    getGrid()[i][j] = new PredatorPreyCell(i, j, getMaxGridPaneSize() / this.getColNum(), this.getCellType());
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
                    getGrid()[i][j] = new PredatorPreyCell(i, j, getMaxGridPaneSize() / this.getColNum(), this.getCellType());
                    getGrid()[i][j].setRandStartState();
                }
            }
        }
    }

    private void determineParameters() {
        if(getParameterValues().size() > 0) {
            for(String s : getParameterValues().keySet()) {
                if(s.equals("SharkReproductionCycle")) {
                    sharkReproductionTime = getParameterValues().get(s);
                } else if (s.equals("EnergyFromEatingFish")) {
                    energyGainedFromFish = getParameterValues().get(s);
                } else if (s.equals("FishReproductionCycle")) {
                    fishReproductionTime = getParameterValues().get(s);
                } else if (s.equals("SharkEnergy")) {
                    maxSharkEnergy = getParameterValues().get(s);
                }
            }
        }
        if(fishReproductionTime <= 0) {
            fishReproductionTime = DEFAULT_FISH_REPRODUCTION_TIME;
        } else if (sharkReproductionTime <= 0) {
            sharkReproductionTime = DEFAULT_SHARK_REPRODUCTION_TIME;
        } else if (energyGainedFromFish <= 0) {
            energyGainedFromFish = DEFAULT_ENERGY_FROM_EATING_FISH;
        } else if (maxSharkEnergy <= 0) {
            maxSharkEnergy = DEFAULT_MAX_SHARK_ENERGY;
        }
    }
}
