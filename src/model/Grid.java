package model;

import java.util.*;

/**
 *
 * @author Duy Tieu, Samuel Appiah-Kubi, Austin Kao
 */
public class Grid {
    private static final double MAX_GRID_PANE_SIZE = 360;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources/"; //May have to delete
    private static final String DEFAULT_XML_FILE = "Game_Of_life.xml";

    private XMLReader reader;
    private Cell[][] grid;
    private int size;
    protected String configFileName;
    protected String defaultFileName;
    private ArrayList<String> states;
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
        states = new ArrayList<>();
        var = new ArrayList<>();
        counts = new ArrayList<>();
        val = new ArrayList<>();
        reader.loadDoc(fileName, defaultFile);
        //size = reader.determineGridSize(size);
        System.out.println(size);
        //reader.addParameters(var, val);
        reader.addCell(states, counts);
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
                grid[i][j].setCurrState(grid[i][j].getNextState());
            }
        }
    }

    public void fillGrid() {
        if (counts.size() > 0 && states.size() > 0 && counts.size() == states.size()) {
            TreeMap<Integer, Integer> cellTypeCount = new TreeMap<>();
            for (int k = 0; k < counts.size(); k++) {
                cellTypeCount.put(k, counts.get(k));
                //System.out.println(states.get(k));
            }
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    while(true) {
                        int rn = new Random().nextInt(states.size());
                        if(cellTypeCount.get(rn) > 0) {
                            grid[i][j] = new GOLCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum());
                            int newCount = cellTypeCount.get(rn) - 1;
                            cellTypeCount.remove(rn);
                            cellTypeCount.put(rn, newCount);
                            StateENUM state = StateENUM.valueOf(states.get(rn));
                            grid[i][j].setStartState(state);
                            break;
                        }
                    }
                }
            }
        } else {
            System.out.println("Switching to random cell setup");
            for (int i = 0; i < this.getRowNum(); i++) {
                for (int j = 0; j < this.getColNum(); j++) {
                    grid[i][j] = new GOLCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum());
                    grid[i][j].setRandStartState();
                }
            }
        }
    }

    public void storeNeighbors(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();
        System.out.println(cell.getRowPos());
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
    public int getRowNum() {
        return grid.length;
    }
    public int getColNum() {
        return grid[0].length;
    }
    public Cell getCell (int row, int col) {
        return grid[row][col];
    }
    public Cell[][] getGrid() {
        return grid;
    }
    protected double getMaxGridPaneSize() {
        return MAX_GRID_PANE_SIZE;
    }
    public ArrayList<String> getCellStates(){
        return states;
    }
    public ArrayList<Integer> getCellCounts() {
        return counts;
    }
}
