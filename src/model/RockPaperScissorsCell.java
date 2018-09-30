package model;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class RockPaperScissorsCell extends Cell{
    private StateENUM[] states = {StateENUM.WHITESPACE, StateENUM.BLUE, StateENUM.RED, StateENUM.GREEN};

    public RockPaperScissorsCell(int row, int col, double width, String cellType) {
        super(row, col, width, cellType);
    }

    @Override
    public void updateCell() {
        ArrayList<Cell> neighborList = getNeighbors();
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
}
