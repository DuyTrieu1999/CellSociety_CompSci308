package model;

import java.util.*;
import javafx.scene.paint.Color;
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
    private PredatorPreyCell move;
    private boolean canMove;
    private boolean fishNeighbor;
    private double cellWidth;

    public PredatorPreyCell(int row, int col, double width) {
        super(row, col, width);
        canMove = false;
        cellWidth = width;
    }
    @Override
    public void updateCell () {
        canMove = false;
        fishNeighbor = false;
        if(this.getCurrState() == StateENUM.WATER && this.getNextState() != StateENUM.SHARK) {
            this.setNextState(StateENUM.WATER);
            return;
        } else if (this.getCurrState() == StateENUM.SHARK) {

        }
        TreeMap<Integer, PredatorPreyCell> currNeighborsMap = new TreeMap<Integer, PredatorPreyCell>();
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        int index = 0;
        for(Cell neighbor : currNeighbors) {
            if(neighbor.getCurrState() != this.getCurrState() && !(neighbor.getCurrState() == StateENUM.SHARK && this.getCurrState() == StateENUM.FISH)) {
                canMove = true;
            }
            if(this.getCurrState() == StateENUM.SHARK && neighbor.getCurrState() == StateENUM.FISH) {
                fishNeighbor = true;
            }
            PredatorPreyCell mappedNeighbor= new PredatorPreyCell(getRowPos(),getColPos(), cellWidth);
            mappedNeighbor.setCurrState(neighbor.getCurrState());
            mappedNeighbor.setNextState(neighbor.getNextState());
            currNeighborsMap.put(index, mappedNeighbor);
            index++;
        }
        if(canMove) {
            //For when canMove
            if(this.getCurrState() == StateENUM.FISH) {
                do {
                    int rand = new Random().nextInt(currNeighbors.size());
                    move = currNeighborsMap.get(rand);
                    if(move.getCurrState() == StateENUM.VACANT && move.getNextState() != StateENUM.SHARK) {
                        move.setNextState(this.getCurrState());
                    }
                } while(move.getCurrState() != StateENUM.VACANT);
            } else if(this.getCurrState() == StateENUM.SHARK) {
                if(fishNeighbor) {
                    do {
                        int rand = new Random().nextInt(currNeighbors.size());
                        move = currNeighborsMap.get(rand);
                        if(move.getCurrState() == StateENUM.FISH) {
                            move.setNextState(this.getCurrState());
                        }
                    } while(move.getCurrState() != StateENUM.FISH);
                }
                if(this.getNextState() != null && this.getNextState() == this.getCurrState()) {
                    this.setNextState(StateENUM.WATER);
                }
            }
        } else {
            this.setNextState(this.getCurrState());
        }
        this.setFill(getStateColor(this.getNextState()));
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
        this.setFill(getStateColor(this.getCurrState()));
    }

    @Override
    public PredatorPreyCell getMove() {
        if(move != null) {
            return move;
        } else {
            return null;
        }
    }
    @Override
    public boolean isEating() {
        return fishNeighbor;
    }
    @Override
    public boolean isMoving() {
        return canMove;
    }

    public Cell swapCells(PredatorPreyCell cell) {
        PredatorPreyCell temp = cell;
        cell = this;
        return temp;
    }

//    private int getReproductionTime() { return reproductionTime;}
//    private int getSharkEnergy() {return sharkEnergy;}

}
