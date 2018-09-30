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
    private StateENUM[] waTorWorldCellStates = {StateENUM.FISH, StateENUM.WATER, StateENUM.SHARK};
    private boolean hasFish = false;
    private boolean hasShark = false;
    private String cellType;
    private double cellWidth;

    public PredatorPreyCell(int row, int col, double width, String cellType) {
        super(row, col, width, cellType);
        cellWidth = width;
        for(int i = 0; i < waTorWorldCellStates.length; i++) {
            getCellStateEnums().add(waTorWorldCellStates[i]);
        }
    }
    @Override
    public void updateCell () {
        if(this.hasFish) {
            this.setNextState(StateENUM.FISH);
            hasFish = false;
        } else if (this.hasShark) {
            this.setNextState(StateENUM.SHARK);
            hasShark = false;
        } else {
            this.setNextState(StateENUM.WATER);
        }
        this.setFill(this.getStateColor(this.getNextState()));
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
    public void setHasFish(boolean value) {
        hasFish = value;
    }
    @Override
    public void setHasShark(boolean value) {
        hasShark = value;
    }
}
