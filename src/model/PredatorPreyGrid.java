package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the grid for the Wa-Tor World of Predator Prey Relationships
 * @author Austin Kao, Duy Trieu
 */
public class PredatorPreyGrid extends Grid{
    private final int SHARK_REPRODUCTION_CYCLE_WAIT = 5;
    private final int MAX_SHARK_ENERGY = 3;
    private final int FISH_REPRODUCTION_CYCLE_WAIT = 3;
    private final int ENERGY_FROM_EATING_FISH = 2;

    private HashMap<Integer, Fish> poorInnocentLittleFishies; //Rename? lol
    private HashMap<Integer, Shark> sharks;

    public PredatorPreyGrid(int size) {
        super(size);
        poorInnocentLittleFishies = new HashMap<>();
        sharks = new HashMap<Integer, Shark>();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                if(getGrid()[i][j].getCurrState() == StateENUM.FISH) {
                    poorInnocentLittleFishies.put(hashCode(i,j), new Fish());
                } else if(getGrid()[i][j].getCurrState() == StateENUM.SHARK) {
                    sharks.put(hashCode(i,j), new Shark());
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
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                if(getGrid()[i][j].getCurrState() == StateENUM.SHARK) {
                    getGrid()[i][j].updateCell();
                    if(getGrid()[i][j].isMoving()) {
                        PredatorPreyCell nextLocation = getGrid()[i][j].getMove();
                        Shark currentShark = sharks.get(hashCode(i,j));
                        if(getGrid()[i][j].isEating()) {
                            poorInnocentLittleFishies.remove(hashCode(nextLocation.getRowPos(),nextLocation.getColPos()));
                            nextLocation.setCurrState(StateENUM.WATER);
                            currentShark.setSharkEnergy(currentShark.getSharkEnergy()+ENERGY_FROM_EATING_FISH);
                        }
                        sharks.remove(hashCode(i,j));
                        if(currentShark.getReproductionTime() <= 0) {
                            currentShark.setReproductionTime(SHARK_REPRODUCTION_CYCLE_WAIT);
                            sharks.put(hashCode(i,j), new Shark());
                        } else {
                            currentShark.setReproductionTime(currentShark.getReproductionTime()-1);
                        }
                        sharks.put(hashCode(nextLocation.getRowPos(),nextLocation.getColPos()), currentShark);
                    } else {
                        Shark currentShark = sharks.get(hashCode(i,j));
                        if(currentShark.getReproductionTime() != 0) {
                            currentShark.setReproductionTime(currentShark.getReproductionTime() - 1);
                            currentShark.setSharkEnergy(currentShark.getSharkEnergy() - 1);
                        }
                    }
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                if(getGrid()[i][j].getCurrState() == StateENUM.FISH) {
                    getGrid()[i][j].updateCell();
                    if(getGrid()[i][j].isMoving()) {
                        PredatorPreyCell nextLocation = getGrid()[i][j].getMove();
                        Fish currentFish = poorInnocentLittleFishies.get(hashCode(i,j));
                        poorInnocentLittleFishies.remove(hashCode(i,j));
                        if(currentFish.getReproductionTime() <= 0) {
                            currentFish.setReproductionTime(SHARK_REPRODUCTION_CYCLE_WAIT);
                            poorInnocentLittleFishies.put(hashCode(i,j), new Fish());
                        } else {
                            currentFish.setReproductionTime(currentFish.getReproductionTime()-1);
                        }
                        poorInnocentLittleFishies.put(hashCode(nextLocation.getRowPos(),nextLocation.getColPos()), currentFish);
                    } else {
                        Fish currentFish = poorInnocentLittleFishies.get(hashCode(i,j));
                        if(currentFish.getReproductionTime() != 0) {
                            currentFish.setReproductionTime(currentFish.getReproductionTime() - 1);
                        }
                    }
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                getGrid()[i][j].setCurrState(getGrid()[i][j].getNextState());
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
        private int reproductionTime;
        public Fish() {
            reproductionTime = FISH_REPRODUCTION_CYCLE_WAIT;
        }
        public Fish(int reproduction) {
            reproductionTime = reproduction;
        }
        public int getReproductionTime() {
            return reproductionTime;
        }
        public void setReproductionTime(int reproductionTime) {
            this.reproductionTime = reproductionTime;
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
        private boolean hasDied;
        public Shark() {
            sharkEnergy = MAX_SHARK_ENERGY;
            reproductionTime = SHARK_REPRODUCTION_CYCLE_WAIT;
        }
        public Shark(int energy, int reproduction) {
            sharkEnergy = energy;
            reproductionTime = reproduction;
        }
        public int getReproductionTime() {
            return reproductionTime;
        }
        public int getSharkEnergy() {
            return sharkEnergy;
        }
        public void setReproductionTime(int reproductionTime) {
            this.reproductionTime = reproductionTime;
        }
        public void setSharkEnergy(int energy) {
            if(energy < MAX_SHARK_ENERGY) {
                this.sharkEnergy = energy;
            } else {
                this.sharkEnergy = MAX_SHARK_ENERGY;
            }
        }
        public boolean isDead() {
            if(getSharkEnergy() <= 0) {
                return true;
            } else return false;
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
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                this.getGrid()[i][j] = new PredatorPreyCell(i, j, getMaxGridPaneSize() / this.getColNum());
                this.getGrid()[i][j].setStartState();
            }
        }
    }
}
