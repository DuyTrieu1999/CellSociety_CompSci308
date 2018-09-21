package view;
/**
 *
 * @author duytrieu
 */
public enum SceneENUM {
    HBOX_GRID(10),
    BUTTON_GRID(240),
    SCENE_WIDTH(800),
    SCENE_HEIGHT(500),
    GRID_WIDTH(400),
    GRID_HEIGHT(400),
    PADDING(40);

    double sceneVal;
    SceneENUM(double sceneVal) {
        this.sceneVal = sceneVal;
    }
    public double getVal() {
        return sceneVal;
    }
}
