package model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the Wa-Tor World of predator-prey relationships model. **Requires research?
 */

public class PredatorPreyCell extends Cell {
    //Temporary parameters?
    private final int KELP = 0;
    private final int FISH = 1;
    private final int SHARK = 2;

    public PredatorPreyCell(int row, int col, double width) {
        super(row, col, width);
    }
    public void updateCell () {

    }
}
