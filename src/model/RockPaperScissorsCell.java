package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class RockPaperScissorsCell extends Cell{
    private StateENUM[] rpsStates = {StateENUM.WHITESPACE, StateENUM.BLUE, StateENUM.RED, StateENUM.GREEN};
    private int level;

    public RockPaperScissorsCell(int row, int col, double width, String cellType) {
        super(row, col, width, cellType);
        for(int i = 0; i < rpsStates.length; i++) {
            getCellStateEnums().add(rpsStates[i]);
        }
        if(this.getCurrState() != StateENUM.WHITESPACE) {
            level = 0;
        } else {
            level = -1;
        }
    }

    @Override
    public void updateCell() {
        ArrayList<Cell> neighborList = getNeighbors();
        int rn = new Random().nextInt(neighborList.size());
        Cell neighbor = neighborList.get(rn);
        RockPaperScissorsCell chosenOne = (RockPaperScissorsCell) neighbor;
        if(this.getCurrState() == StateENUM.BLUE) {

        } else if(this.getCurrState() == StateENUM.RED) {

        } else if(this.getCurrState() == StateENUM.GREEN) {

        } else {
            if(chosenOne.getCurrState() != StateENUM.WHITESPACE) {
                this.setNextState(chosenOne.getCurrState());
                this.setLevel(chosenOne.getLevel());
            }
        }
    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch (state) {
            case WHITESPACE:
                return Color.WHITE;
            case BLUE:
                return Color.BLUE;
            case RED:
                return Color.RED;
            case GREEN:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    private void setLevel(int value) {
        level = value;
    }

    private int getLevel() {
        return level;
    }
}
