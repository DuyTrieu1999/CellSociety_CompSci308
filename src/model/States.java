package model;

import javafx.scene.paint.Color;

public enum States {
    ALIVE,
    DEAD,
    RACE1,
    RACE2,
    RACE3;

    public static Color stateColor(States state) {
        switch(state) {
            case ALIVE:
                return Color.WHITE;
            case DEAD:
                return Color.BLACK;
            case RACE1:
                return Color.BLUE;
            case RACE2:
                return Color.WHITE;
            case RACE3:
                return Color.RED;
            default:
                return null;
        }
    }



}
