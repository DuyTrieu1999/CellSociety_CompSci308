package model;

import java.util.*;
/**
 * This class implements a cell that would be part of the Game of Life simulation. Most of the initial testing should be with this simulation.
 * @author Austin Kao
 */
public class GOLCell extends Cell{
    private int numAlive;

    public GOLCell(int row, int col) {
        super(row, col);
    }

    public GOLCell updateCell(GOLCell cell) {
        ArrayList<GOLCell>() currNeighbors = cell.getNeighbors();
        for(GOLCell neighbor : currNeighbors) {
            if(neighbor.getCurrState.equals("Alive")) {
                numAlive++;
            }
        }
        if(this.getCurrState.equals("Alive") && (numAlive < 1 || numAlive > 3)) {
            this.setPrevState("Alive");
            this.setCurrState("Dead");
        } else if(this.getCurrState.equals("Dead") && numAlive == 3) {
            this.setPrevState("Dead");
            this.setCurrState("Alive");
        } else {
            this.setPrevState(this.getCurrState());
        }
    }
}
