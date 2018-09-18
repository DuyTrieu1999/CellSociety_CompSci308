package view;

public enum SceneENUM {
    HBOX_GRID(10),
    BUTTON_GRID(240),
    SCENE_WIDTH(800),
    SCENE_HEIGHT(700);

    double sceneVal;
    SceneENUM(double sceneVal) {
        this.sceneVal = sceneVal;
    }
    public double getVal() {
        return sceneVal;
    }
}
