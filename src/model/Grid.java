package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 * @author duytrieu
 */
public class Grid {

    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/"; //May have to delete
    private static final String DEFAULT_XML_FILE = "Game_Of_life.xml";
    private XMLReader reader;
    private Cell[][] grid;
    private int size;
    protected String configFileName;
    protected String defaultFileName;
    private ArrayList<String> state;
    private ArrayList<String> var;
    private ArrayList<Integer> counts;
    private ArrayList<Double> val;

    public Grid (String filename, int size) {
        reader = new XMLReader();
        this.size = size;
        grid = new Cell[size][size];
        configFileName = filename;
        defaultFileName = DEFAULT_XML_FILE;
        loadConfig(configFileName, defaultFileName);
        fillGrid();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                storeNeighbors(grid[i][j]);
            }
        }
    }
    public void loadConfig(String fileName, String defaultFile) {
        System.out.println("Started loading");
        state = new ArrayList<>();
        var = new ArrayList<>();
        counts = new ArrayList<>();
        val = new ArrayList<>();
        reader.loadDoc(fileName, defaultFile);
        reader.determineGridSize(size);
        reader.addVariable(var, val);
        reader.addCell(state, counts);
    }
    public void changeConfig (String configName) {
        configFileName = configName;
        loadConfig(configName, defaultFileName);
    }
    public void updateGrid () {
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                grid[i][j].updateCell();
            }
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                Cell cell = grid[i][j];
                cell.setCurrState(cell.getNextState());
            }
        }
    }

    public void fillGrid () {
        for (int i = 0; i<this.getRowNum(); i++) {
            for (int j = 0; j<this.getColNum(); j++) {
                grid[i][j] = new GOLCell(i, j, (double)360 / this.getColNum());
                grid[i][j].setStartState();
            }
        }
    }

    public void storeNeighbors (Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
        int[] rowCoord = {cell.getRowPos(), cell.getRowPos()+1, cell.getRowPos()-1};
        int[] colCoord = {cell.getColPos(), cell.getColPos()+1, cell.getColPos()-1};
        for (int row: rowCoord) {
            for (int col: colCoord) {
                if (row>-1 && col>-1 && row<(this.getRowNum()) && col<(this.getColNum())) {
                    cellNeighbours.add(grid[row][col]);
                }
            }
        }
        cellNeighbours.remove(grid[cell.getRowPos()][cell.getColPos()]);
        cell.setNeighbors(cellNeighbours);
    }
    public boolean outOfBounds (int row, int col) {
        return (row < 0 || row >= getRowNum() || col < 0 || col >= getColNum());
    }
    public int getRowNum () {
        return grid.length;
    }
    public int getColNum () {
        return grid[0].length;
    }
    public Cell getCell (int row, int col) {
        return grid[row][col];
    }
    public Cell[][] getGrid() {
        return grid;
    }
    public int getGridSize() {
        return size;
    }
}
