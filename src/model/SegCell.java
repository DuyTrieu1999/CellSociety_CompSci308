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
    private boolean hasMoved;
    private boolean moveByAgent1;
    private boolean moveByAgent2;
    private StateENUM[] segregationCellStates = {StateENUM.VACANT, StateENUM.AGENT2, StateENUM.AGENT1};

    public SegCell(int row, int col, double width, String cellType) {
        super(row, col, width, cellType);
        hasMoved = false;
        moveByAgent1 = false;
        moveByAgent2 = false;
        for(int i = 0; i < segregationCellStates.length; i++) {
            getCellStateEnums().add(segregationCellStates[i]);
        }
    }

    @Override
    public void updateCell() {
        if(hasMoved) {
            this.setNextState(StateENUM.VACANT);
            hasMoved = false;
        } else if(moveByAgent1) {
            this.setNextState(StateENUM.AGENT1);
            moveByAgent1 = false;
        } else if(moveByAgent2) {
            this.setNextState(StateENUM.AGENT2);
            moveByAgent2 = false;
        } else {
            this.setNextState(this.getCurrState());
        }
        this.setFill(getStateColor(this.getNextState()));
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
    public void setHasMoved(boolean value) {
        hasMoved = value;
    }

    @Override
    public void setMoveByAgent1(boolean value) {
        moveByAgent1 = value;
    }

    @Override
    public void setMoveByAgent2(boolean value) {
        moveByAgent2 = value;
    }
}
