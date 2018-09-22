package model;

public class SegCell extends Cell{

    private boolean satisfied;
    private double myThreshold;
    private int numAlike;

    public SegCell(int row, int col, double width) {
        super(row, col, width);
        satisfied = true;
    }

    public void determineSatisfaction() {
        ArrayList<Cell> currNeighbors = this.getNeighbors;
        numAlike = 0;
        for(Cell neighbor = currNeighbors) {
            if(this.getCurrState() == neighbor.getCurrState()) {
                numAlike++;
            }
        }
        if(numAlike > myThreshold*currNeighbors.size()) {
            satisfied = true;
        }
    }

    public void updateCell() {

    }

    public int setThreshold(double threshold) {
        myThreshold = threshold;
    }
}
