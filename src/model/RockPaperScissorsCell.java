package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

public class RockPaperScissorsCell extends Cell{
    private static final int MAX_LEVEL = 9;
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
        if(!(neighbor instanceof RockPaperScissorsCell)) {
            System.out.println("Error");
            this.setNextState(this.getCurrState());
            return;
        }
        RockPaperScissorsCell chosenOne = (RockPaperScissorsCell) neighbor;
        if(this.getCurrState() == StateENUM.WHITESPACE) {
            if(chosenOne.getCurrState() != StateENUM.WHITESPACE) {
                this.setNextState(chosenOne.getCurrState());
                this.setLevel(chosenOne.getLevel() + 1);
            } else {
                this.setNextState(this.getCurrState());
            }
        }
        updateLevel(this, chosenOne, this.getCurrState(), chosenOne.getCurrState());
        if(this.getLevel() > MAX_LEVEL) {
            this.setLevel(MAX_LEVEL);
            this.setNextState(chosenOne.getCurrState());
        } else if(chosenOne.getLevel() > MAX_LEVEL) {
            chosenOne.setLevel(MAX_LEVEL);
            chosenOne.setNextState(this.getCurrState());
        } else {
            this.setNextState(this.getCurrState());
        }
    }

    private void updateLevel(RockPaperScissorsCell rps1, RockPaperScissorsCell rps2, StateENUM state1, StateENUM state2) {
        if(state1 == state2) {
            rps1.setNextState(state1);
            return;
        } else if((state1 == StateENUM.BLUE && state2 == StateENUM.GREEN) || (state1 == StateENUM.GREEN && state2 == StateENUM.RED) || (state1 == StateENUM.RED && state2 == StateENUM.BLUE)) {
            rps1.setLevel(rps1.getLevel() - 1);
            rps2.setLevel(rps2.getLevel() + 1);
            rps1.setNextState(state1);
        } else {
            rps1.setLevel(rps1.getLevel() + 1);
            rps2.setLevel(rps2.getLevel() - 1);
            rps2.setNextState(state2);
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
        if(value < 0) {
            level = 0;
        } else {
            level = value;
        }
    }

    private int getLevel() {
        return level;
    }
}
