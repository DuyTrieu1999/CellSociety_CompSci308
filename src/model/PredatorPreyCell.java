package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model.
 * States:
 * FISH represents a cell occupied by fish
 * SHARK represents a cell occupied by a shark
<<<<<<< HEAD
 * WATER represents an empty cell in the sea
=======
 * SEA represents an empty cell in the sea
>>>>>>> master
 * @author Austin Kao
 */

public class PredatorPreyCell extends Cell {
    private int reproductionTime;
    private int sharkEnergy;
    private final int FISH_REPRODUCTION_CYCLE_WAIT = 3;
    private final int SHARK_REPRODUCTION_CYCLE_WAIT = 5;
    private final int MAX_SHARK_ENERGY = 3;
    private StateENUM[] states = {StateENUM.FISH, StateENUM.WATER, StateENUM.SHARK};
    private boolean hasEaten;
    private Cell move;

    public PredatorPreyCell(int row, int col, double width) {
        super(row, col, width);
    }
    @Override
    public void updateCell () {
        if(this.getCurrState() == StateENUM.WATER) {
            this.setNextState(StateENUM.WATER);
            return;
        }
        boolean canMove = false;
        TreeMap<Integer, Cell> currNeighborsMap = new TreeMap<Integer, Cell>();
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        int index = 0;
        for(Cell neighbor : currNeighbors) {
            currNeighborsMap.put(index, neighbor);
            index++;
        }
        while(!canMove) {
            int rand = new Random().nextInt(currNeighbors.size());
            move = currNeighborsMap.get(rand);
            if(!(currNeighborsMap.get(rand).getCurrState() == StateENUM.SHARK && this.getCurrState() == StateENUM.FISH) && (this.getCurrState() != currNeighborsMap.get(rand).getCurrState())) {
                canMove = true;
            }
        }
    }
    public void setReproductionTime(int fishMatingCycleWait) {
        reproductionTime = fishMatingCycleWait;
    }

    public void setSharkEnergy(int sharkStrength) {
        sharkEnergy = sharkStrength;
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case FISH:
                return Color.GREEN;
            case SHARK:
                return Color.GRAY;
            case WATER:
                return Color.BLUE;
            default:
                return null;
        }
    }

    @Override
    public void setStartState() {
        int rand = new Random().nextInt(states.length);
        this.setCurrState(states[rand]);
    }

    public Cell swapCells(PredatorPreyCell cell) {
        PredatorPreyCell temp = cell;
        cell = this;
        return temp;
    }
}
