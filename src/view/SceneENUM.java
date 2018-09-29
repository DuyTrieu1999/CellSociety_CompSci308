package view;
/**
 *
 * @author duytrieu
 */
public enum SceneENUM {
    HBOX_GRID(10),
    BUTTON_GRID(240),
    SCENE_WIDTH(1200),
    SCENE_HEIGHT(500),
    GRID_WIDTH(400),
    GRID_HEIGHT(400),
    PADDING(30),
    SIZE_SLIDER_MAX(20),
    SIZE_SLIDER_MIN(10),
    SIZE_SLIDER_VAL(15),
    SPEED_SLIDER_MAX(2),
    SPEED_SLIDER_MIN(1),
    SPEED_SLIDER_VAL(1),
    GRID_PANE_PADDING(60);


    double sceneVal;
    SceneENUM(double sceneVal) {
        this.sceneVal = sceneVal;
    }
    public double getVal() {
        return sceneVal;
    }
}
