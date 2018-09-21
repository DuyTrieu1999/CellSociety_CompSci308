package model;

import java.util.*;

public class GOLCell extends Cell{
    private int numAlive;

    public GOLCell(int row, int col) {
        super(row, col);
    }

    public GOLCell updateCell(GOLCell cell) {
        ArrayList<GOLCell>() currNeighbors = cell.getNeighbors();
        for(GOLCell neighbor : currNeighbors) {
            if(neighbor.get)
        }
    }
}
