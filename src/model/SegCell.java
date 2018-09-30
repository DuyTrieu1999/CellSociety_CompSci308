package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This cell represents a cell in the Schelling's model of segregation simulation.
 * States:
 * VACANT represents a cell with no agents that occupy the space
 * AGENT1, AGENT2 represent the two types of agents that will segregate
 * @author Austin Kao
 */

public class SegCell extends Cell{
    private boolean satisfied;
    private double myThreshold;
    private double numAlike;
    private double agentNeighbors;
    private StateENUM[] segregationCellStates = {StateENUM.VACANT, StateENUM.AGENT2, StateENUM.AGENT1};

    public SegCell(int row, int col, double width, String cellType) {
        super(row, col, width, cellType);
        satisfied = true;
        for(int i = 0; i < segregationCellStates.length; i++) {
            getCellStateEnums().add(segregationCellStates[i]);
        }
    }

    @Override
    public boolean isSatisfied() {
        ArrayList<Cell> currNeighbors = this.getNeighbors();
        numAlike = 0;
        agentNeighbors = 0;
        for(Cell neighbor : currNeighbors) {
            if(neighbor.getCurrState() == StateENUM.AGENT1 || neighbor.getCurrState() == StateENUM.AGENT2) {
                agentNeighbors++;
            }
            if(this.getCurrState() == neighbor.getCurrState()) {
                numAlike++;
            }
        }
        if(myThreshold >= numAlike/agentNeighbors) {
            satisfied = true;
        } else {
            satisfied = false;
        }
        return satisfied;
    }

    @Override
    public void updateCell() {
        if(!satisfied) {
            this.setNextState(StateENUM.VACANT);
            this.setCurrState(StateENUM.VACANT);
        } else {
            this.setNextState(this.getCurrState());
        }
        this.setFill(getStateColor(this.getNextState()));
    }

    @Override
    public void setThreshold(double threshold) {
        myThreshold = threshold;
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case VACANT:
                return Color.WHITE;
            case AGENT1:
                return Color.BLUE;
            case AGENT2:
                return Color.YELLOW;
            default:
                return null;
        }
    }

    @Override
    public void setSatisfaction(boolean value) {
        satisfied = value;
    }
}
