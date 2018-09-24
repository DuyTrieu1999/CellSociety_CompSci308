package model;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model.
 * States:
 * FISH represents a cell occupied by fish
 * SHARK represents a cell occupied by a shark
 * WATER represents an empty cell in the sea
 * @author Austin Kao, Duy Trieu
 */

public class PredatorPreyCell extends Cell {
    private StateENUM[] states = {StateENUM.FISH, StateENUM.WATER, StateENUM.SHARK};
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
        if(this.getCurrState() == StateENUM.WATER && (this.getNextState() != StateENUM.SHARK || this.getNextState() != StateENUM.FISH)) {
            this.setNextState(StateENUM.WATER);
            return;
        }
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        ArrayList<Cell> waterNeighbors = getWater(this);
        ArrayList<Cell> fishNeighbors = getFish(this);
        TreeMap<Integer, PredatorPreyCell> currNeighborsMap = getMap(currNeighbors);
        TreeMap<Integer, PredatorPreyCell> currWaterMap = getMap(waterNeighbors);
        TreeMap<Integer, PredatorPreyCell> currFishMap = getMap(fishNeighbors);
        if (this.getCurrState() == StateENUM.FISH) {
            if (hasShark(currNeighbors)) {
                this.setNextState(StateENUM.WATER);
            }
            else {
                if (hasWater(currNeighbors)) {
                    canMove = hasWater(currNeighbors);
                    int rand = new Random().nextInt(waterNeighbors.size());
                    move = currWaterMap.get(rand);
                    move.setNextState(this.getCurrState());
                    this.setNextState(StateENUM.WATER);
                }
                else {
                    this.setNextState(this.getCurrState());
                }
            }
        }
        if (this.getCurrState() == StateENUM.SHARK) {
            if (hasFish(currNeighbors)) {
                fishNeighbor = hasFish(currNeighbors);
                int rand = new Random().nextInt(fishNeighbors.size());
                move = currFishMap.get(rand);
                move.setNextState(this.getCurrState());
                this.setNextState(StateENUM.WATER);
            }
            else {
                if (hasWater(currNeighbors)) {
                    canMove = hasWater(currNeighbors);
                    int rand = new Random().nextInt(waterNeighbors.size());
                    move = currWaterMap.get(rand);
                    move.setNextState(this.getCurrState());
                    this.setNextState(StateENUM.WATER);
                }
                else {
                    this.setNextState(this.getCurrState());
                }
            }
        }
        this.setFill(getStateColor(this.getNextState()));
    }
    public boolean hasShark (ArrayList<Cell> neighbors) {
        for (Cell cell: neighbors) {
            if (cell.getCurrState() == StateENUM.SHARK)
                return true;
            return false;
        }
        return false;
    }
    public boolean hasWater (ArrayList<Cell> neighbors) {
        for (Cell cell: neighbors) {
            if (cell.getCurrState() == StateENUM.WATER && !(cell.getCurrState() == StateENUM.SHARK && cell.getCurrState() == StateENUM.FISH)) {
                return true;
            }
            return false;
        }
        return false;
    }
    public boolean hasFish (ArrayList<Cell> neighbors) {
        for (Cell cell: neighbors) {
            if (cell.getCurrState() == StateENUM.FISH) {
                return true;
            }
            return false;
        }
        return false;
    }
    public TreeMap<Integer, PredatorPreyCell> getMap (ArrayList<Cell> cellList) {
        TreeMap<Integer, PredatorPreyCell> map = new TreeMap<>();
        int index = 0;
        for(Cell neighbor : cellList) {
            PredatorPreyCell mappedNeighbor= new PredatorPreyCell(getRowPos(),getColPos(), cellWidth);
            mappedNeighbor.setCurrState(neighbor.getCurrState());
            mappedNeighbor.setNextState(neighbor.getNextState());
            map.put(index, mappedNeighbor);
            index++;
        }
        return map;
    }
    public ArrayList<Cell> getWater (Cell cell) {
        ArrayList<Cell> neighbors = cell.getNeighbors();
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell c: neighbors) {
            if (c.getCurrState() == StateENUM.WATER) {
                result.add(c);
            }
        }
        return result;
    }
    public ArrayList<Cell> getFish (Cell cell) {
        ArrayList<Cell> neighbors = cell.getNeighbors();
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell c: neighbors) {
            if (c.getCurrState() == StateENUM.FISH) {
                result.add(c);
            }
        }
        return result;
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
                return Color.BLACK;
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
}
