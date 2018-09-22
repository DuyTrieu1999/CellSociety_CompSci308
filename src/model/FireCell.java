package model;

import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Random;

public class FireCell extends Cell {
    private double probCatch;

    public FireCell (int row, int col, double width) {
        super(row, col, width);
    }

    public void updateCell() {
        if(this.getCurrState() == 1) {
            double rn = Math.random();
            this.setPrevState(1);
            if(rn < probCatch) {
                this.setCurrState(2);
            }
        }
    }
}
