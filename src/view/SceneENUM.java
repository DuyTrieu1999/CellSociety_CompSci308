package view;
/**
 *
 * @author duytrieu
 */
public enum SceneENUM {
    HBOX_GRID(10),
    BUTTON_GRID(240),
    SCENE_WIDTH(1300),
    SCENE_HEIGHT(600),
    GRID_WIDTH(400),
    GRID_HEIGHT(400),
    PADDING(50),
    GRAPH_HEIGHT(300),
    SIZE_SLIDER_MAX(50),
    SIZE_SLIDER_MIN(10),
    SIZE_SLIDER_VAL(20),
    SPEED_SLIDER_MAX(2),
    SPEED_SLIDER_MIN(1),
    SPEED_SLIDER_VAL(1),
    GRID_PANE_PADDING(60),
    CELL_TYPE_BOX(30);


    double sceneVal;
    SceneENUM(double sceneVal) {
        this.sceneVal = sceneVal;
    }
    public double getVal() {
        return sceneVal;
    }
}
