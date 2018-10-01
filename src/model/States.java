package model;

import javafx.scene.paint.Color;

public enum States {
    ALIVE,
    DEAD,
    VACANT,
    AGENT1,
    AGENT2,
    DEFORESTED,
    TREE,
    BURNING,
    FISH,
    SHARK,
    WATER;

    public static Color stateColor(States state) {
        switch(state) {
            case ALIVE:
                return Color.WHITE;
            case DEAD:
                return Color.BLACK;
            case VACANT:
                return Color.WHITE;
            case AGENT1:
                return Color.BLUE;
            case AGENT2:
                return Color.YELLOW;
            case DEFORESTED:
                return Color.YELLOW;
            case TREE:
                return Color.GREEN;
            case BURNING:
                return Color.RED;
            case FISH:
                return Color.GREEN;
            case SHARK:
                return Color.GRAY;
            case WATER:
                return Color.BLUE;
            default:
                return null;
        }
    }
}
