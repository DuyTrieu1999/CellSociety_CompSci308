package model;

import java.util.*;

/**
 * The Grid class is the super class for all other simulation grids. The Grid class acts as the grid for the Game of Life simulation by default.
 * @author Duy Tieu, Samuel Appiah-Kubi, Austin Kao
 */
public class Grid {
    private static final double MAX_GRID_PANE_SIZE = 360;
    private static final String DEFAULT_XML_FILE = "Game_Of_life.xml";
    private static final String GOL_SIM_STRING = "Game of Life";
    private static final String FIRE_SIM_STRING = "Spreading of Fire";
    private static final String WATOR_SIM_STRING = "Wa-Tor World model";
    private static final String SEGREGATION_SIM_STRING = "Schelling's Model of Segregation";

    private XMLReader reader;
    private Cell[][] grid;
    private int size;
    protected String configFileName;
    protected String defaultFileName;
    private ArrayList<String> states;
    private ArrayList<Integer> counts;
    private TreeMap<String, Double> parameterValues;
    private TreeMap<StateENUM, Integer> populationMap = new TreeMap<>();
    private ArrayList<String> saveState; //Consider turning into a hashMap
    private String cellType;
    private String simType;
    private String simDescription;

    public Grid (String filename, int size, String cellType) {
        this.cellType = cellType;
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
        states = new ArrayList<>();
        counts = new ArrayList<>();
        parameterValues = new TreeMap<>();
        saveState = new ArrayList<>();
        reader.loadDoc(fileName, defaultFile);
        simType = reader.readSimType();
        simDescription = reader.readDescription();
        reader.addParameters(parameterValues);
        reader.addCell(states, counts);
        reader.loadSave(saveState);
    }
    public void changeConfig (String configName) {
        configFileName = configName;
        loadConfig(configName, defaultFileName);
        fillGrid();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                storeNeighbors(grid[i][j]);
            }
        }
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
    public TreeMap<StateENUM, Integer> getPopulationMap () {
        for (int i=0; i<states.size(); i++) {
            populationMap.put(StateENUM.valueOf(states.get(i)), 0);
        }
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                Cell cell = grid[i][j];
                if (populationMap.containsKey(cell.getCurrState())) {
                    populationMap.put(cell.getCurrState(), populationMap.get(cell.getCurrState()) + 1);
                }
                else {
                    populationMap.put(cell.getCurrState(), 1);
                }
            }
        }
        return this.populationMap;
    }

    public void fillGrid() {
        if(saveState.size() > 0) {
            setUpSavedGrid(simType);
        } else {
            int cellCount = 0;
            for(int i = 0; i < counts.size(); i++) {
                cellCount += counts.get(i);
            }
            if (counts.size() > 0 && states.size() > 0 && counts.size() == states.size() && Math.pow(size, 2) <= cellCount) {
                setUpGridFromXMLConfig(simType);
            } else {
                System.out.println("Switching to random cell setup");
                setUpRandomGrid(simType);
            }
        }
    }

    public void setUpSavedGrid(String gridType) {
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                int index = getRowNum()*i+j;
                if (gridType.equals(GOL_SIM_STRING)) {
                    grid[i][j] = new GOLCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                if (gridType.equals(WATOR_SIM_STRING)) {
                    grid[i][j] = new PredatorPreyCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                if (gridType.equals(FIRE_SIM_STRING)) {
                    grid[i][j] = new FireCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                if (gridType.equals(SEGREGATION_SIM_STRING)) {
                    grid[i][j] = new SegCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                grid[i][j].setStartState(StateENUM.valueOf(saveState.get(index)));
            }
        }
    }

    public void setUpGridFromXMLConfig(String gridType) {
        int total = 0;
        TreeMap<String, Integer> cellTypeCount = new TreeMap<>();
        for (int k = 0; k < counts.size(); k++) {
            cellTypeCount.put(states.get(k), counts.get(k));
            total += counts.get(k);
        }
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                boolean createdCell = false;
                while(!createdCell) {
                    int rn = new Random().nextInt(total);
                    for (String s : cellTypeCount.keySet()) {
                        if(rn > cellTypeCount.get(s)) {
                            int value = cellTypeCount.get(s);
                            rn = rn - value;
                        } else {
                            if(cellTypeCount.get(s) > 0) {
                                if (gridType.equals(GOL_SIM_STRING)) {
                                    grid[i][j] = new GOLCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                                }
                                if (gridType.equals(WATOR_SIM_STRING)) {
                                    grid[i][j] = new PredatorPreyCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                                }
                                if (gridType.equals(FIRE_SIM_STRING)) {
                                    grid[i][j] = new FireCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                                }
                                if (gridType.equals(SEGREGATION_SIM_STRING)) {
                                    grid[i][j] = new SegCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                                }
                                int newCount = cellTypeCount.get(s) - 1;
                                StateENUM state = StateENUM.valueOf(s);
                                grid[i][j].setStartState(state);
                                cellTypeCount.replace(s, newCount);
                                createdCell = true;
                                break;
                            }
                        }
                    }
                }
                total--;
            }
        }
    }

    public void setUpRandomGrid(String gridType) {
        for (int i = 0; i < this.getRowNum(); i++) {
            for (int j = 0; j < this.getColNum(); j++) {
                if (gridType.equals(GOL_SIM_STRING)) {
                    grid[i][j] = new GOLCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                if (gridType.equals(WATOR_SIM_STRING)) {
                    grid[i][j] = new PredatorPreyCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                if (gridType.equals(FIRE_SIM_STRING)) {
                    grid[i][j] = new FireCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                if (gridType.equals(SEGREGATION_SIM_STRING)) {
                    grid[i][j] = new SegCell(i, j, MAX_GRID_PANE_SIZE / this.getColNum(), getCellType());
                }
                grid[i][j].setRandStartState();
            }
        }
    }

    public void storeNeighbors(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();
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
    public TreeMap<String, Double> getParameterValues() {
        return parameterValues;
    }
    public ArrayList<String> getSaveState() {
        return saveState;
    }
    public String getCellType () { return cellType; }
    public ArrayList<String> createSaveState() {
        saveState.clear();
        for (int i=0; i<this.getRowNum(); i++) {
            for (int j=0; j<this.getColNum(); j++) {
                saveState.add(getCell(i, j).getCurrState().toString());
            }
        }
        return saveState;
    }
    public int getSize() {
        return size;
    }
    public String getSimType() {
        return simType;
    }
    public void setSize(int newSize) {
        size = newSize;
    }
}
