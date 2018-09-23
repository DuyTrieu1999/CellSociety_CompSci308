package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegCell extends Cell {

    private StateENUM[] states = {StateENUM.VACANT, StateENUM.AGENT1, StateENUM.AGENT2};
    private boolean satisfied;
    private double myThreshold = 0.3;
    private int numAlike;


    public SegCell(int row, int col, double width) {
        super(row, col, width);
    }

//    public void determineSatisfaction() {
//        ArrayList<Cell> currNeighbors = this.getNeighbors();
//        numAlike = 0;
//        for(Cell neighbor : currNeighbors) {
//            if(this.getCurrState() == neighbor.getCurrState()) {
//                numAlike++;
//            }
//        }
//        if(numAlike > myThreshold*currNeighbors.size()) {
//            satisfied = true;
//        } else {
//            satisfied = false;
//        }
//    }
    @Override
    public void updateCell() {

    }

    @Override
    public Color getStateColor(StateENUM state) {
        switch(state) {
            case VACANT:
                return Color.WHITE;
            case AGENT1:
                return Color.BLACK;
            case AGENT2:
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
}
