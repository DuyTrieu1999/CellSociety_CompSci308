package model;

import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model.
 * States:
 * FISH represents a cell occupied by fish
 * SHARK represents a cell occupied by a shark
 * WATER represents an empty cell in the sea
 * @author Austin Kao
 */

public class PredatorPreyCell extends Cell {
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
        if(move != null) {
            return;
        }
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
        if(this.getCurrState() == StateENUM.FISH) {

        }
    }

    public Cell swapCells(PredatorPreyCell cell) {
        PredatorPreyCell temp = cell;
        cell = this;
        return temp;
    }
}
