package view;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class CellTypeButton {
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    protected RadioButton rectangleCellButton;
    protected RadioButton triangleCellButton;
    protected RadioButton hexagonCellButton;
    protected HBox radioBox;
    protected ToggleGroup radioGroup;
    private ResourceBundle myResources;

    public CellTypeButton () {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Button");
        rectangleCellButton = new RadioButton(myResources.getString("Rectangle"));
        rectangleCellButton.setSelected(true);
        triangleCellButton = new RadioButton(myResources.getString("Triangle"));
        hexagonCellButton = new RadioButton(myResources.getString("Hexagon"));

        radioGroup = new ToggleGroup();
        radioBox = new HBox(SceneENUM.CELL_TYPE_BOX.getVal());
        radioBox.getChildren().add(rectangleCellButton);
        rectangleCellButton.setToggleGroup(radioGroup);
        radioBox.getChildren().add(triangleCellButton);
        triangleCellButton.setToggleGroup(radioGroup);
        radioBox.getChildren().add(hexagonCellButton);
        hexagonCellButton.setToggleGroup(radioGroup);
        if (triangleCellButton.isSelected()) {
            
        }
    }
}
