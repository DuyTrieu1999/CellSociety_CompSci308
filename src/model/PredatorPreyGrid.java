package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the grid for the Wa-Tor World of Predator Prey Relationships
 */
public class PredatorPreyGrid extends Grid{
    PredatorPreyCell[][] grid;
    private HashMap<Integer, Fish> poorInnocentLittleFishies; //Must think of HashCodes
    private HashMap<Integer, Shark> sharks;

    public PredatorPreyGrid(int size) {
        super(size);
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                if(grid[i][j].getCurrState() == StateENUM.FISH) {
                    poorInnocentLittleFishies.put(hashCode(i,j), new Fish());
                } else if(grid[i][j].getCurrState() == StateENUM.SHARK) {
                    sharks.put(hashCode(i,j), new Shark());
                }
            }
        }
    }

    @Override
    public void updateGrid() {
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                grid[i][j].updateCell();
                if(poorInnocentLittleFishies.containsKey(hashCode(i,j))) {
                    Fish currentFish = poorInnocentLittleFishies.get(hashCode(i,j));
                    if(currentFish.getReproductionTime() <= 0) {
                        //place code here
                    }
                }
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                grid[i][j].setCurrState(grid[i][j].getNextState());
            }
        }
    }

    public int hashCode(int i, int j) {
        return getRowNum()*i+j;
    }
    /**
     * The Fish class is intended to represent a fish in the simulation.
     * Fish have a reproduction time parameter which represents the number of chronons they take to reproduce
     */
    class Fish {
        private int reproductionTime;
        private final int FISH_REPRODUCTION_CYCLE_WAIT = 3;
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
        private final int SHARK_REPRODUCTION_CYCLE_WAIT = 5;
        private final int MAX_SHARK_ENERGY = 3;
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
            this.sharkEnergy = energy;
        }
        public boolean isDead() {
            if(getSharkEnergy() <= 0) {
                return true;
            } else return false;
        }
    }

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
        cell.setNeighbors(cellNeighbours);
    }
}
