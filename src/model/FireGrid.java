package model;

import javafx.scene.paint.Color;
import java.util.*;

/**
 * The FireGrid class implements the Spreading by Fire simulation
 * @author Austin Kao
 * @author Samuel Appiah-Kubi
 */
public class FireGrid extends Grid {
    private final static double DEFAULT_PROBABILITY = 0.1;
    private double probability;
    //private String cellType;

    public FireGrid(String filename, int size, String cellType) {
        super(filename, size, cellType);
        if(getParameterValues().size() > 0) {
            for(String s : getParameterValues().keySet()) {
                if(s.equals("probabilityOfCatchingFire")) {
                    probability = getParameterValues().get(s);
                }
            }
        } else {
            probability = DEFAULT_PROBABILITY;
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                getGrid()[i][j].setProbCatch(probability);
            }
        }
    }
    //For some reason, when testing the simulation, the other method would not work, so I replaced it with this one.
    @Override
    public void storeNeighbors(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();
        if(!outOfBounds(cell.getRowPos()+1, cell.getColPos())) {
            cellNeighbours.add(getGrid()[cell.getRowPos()+1][cell.getColPos()]);
        }
        if(!outOfBounds(cell.getRowPos()-1, cell.getColPos())) {
            cellNeighbours.add(getGrid()[cell.getRowPos()-1][cell.getColPos()]);
        }
        if(!outOfBounds(cell.getRowPos(), cell.getColPos()+1)) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][cell.getColPos()+1]);
        }
        if(!outOfBounds(cell.getRowPos(), cell.getColPos()-1)) {
            cellNeighbours.add(getGrid()[cell.getRowPos()][cell.getColPos()-1]);
        }
        cell.setNeighbors(cellNeighbours);
    }
}
