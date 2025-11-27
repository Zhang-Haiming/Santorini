package edu.cmu.cs214.hw2;

/**
 * Represents a single space on the Santorini game board
 */
public class Space {
    private int row;
    private int col;
    private int towerLevel;
    private boolean hasDome;
    private Worker occupied;
    private static final int WINNING_TOWER_LEVEL = 3;

    /**
     * Constructor to initialize a space
     * @param row The row index of the space
     * @param col The column index of the space
     */
    public Space(int row, int col, int towerLevel, boolean hasDome, Worker occupied) {
        this.row = row;
        this.col = col;
        this.towerLevel = towerLevel;
        this.hasDome = hasDome;
        this.occupied = occupied;
    }

    public Space(Space other) {
        this.row = other.row;
        this.col = other.col;
        this.towerLevel = other.towerLevel;
        this.hasDome = other.hasDome;
        this.occupied = other.occupied;
    }

    /**
     * Build a tower level or dome on this space
     */
    public void build() {
        if (!this.hasDome && this.towerLevel < WINNING_TOWER_LEVEL) {
            this.towerLevel++;
        } else if (this.towerLevel == WINNING_TOWER_LEVEL && !this.hasDome) {
            this.hasDome = true;
        }
    }

    /**
     * Check if this space can be built on
     * @return True if can build, false otherwise
     */
    public boolean canBuild() {
        return !this.hasDome && this.occupied == null;
    }

    /**
     * Check if this space can be moved to
     * @param worker The worker attempting to move here
     * @return True if can move, false otherwise
     */
    public boolean canMoveTo(Worker worker) {
        return this.occupied == null && !this.hasDome && 
               (this.towerLevel <= worker.getPos().getTowerLevel() + 1);
    }

    /**
     * Set the worker occupying this space
     * @param worker The worker to occupy this space
     */
    public void setOccupied(Worker worker) {
        this.occupied = worker;
    }

    /**
     * Remove the worker from this space
     */
    public void removeWorker() {
        this.occupied = null;
    }

    // Getters
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getTowerLevel() { return towerLevel; }
    public boolean hasDome() { return hasDome; }
    public Worker getOccupied() { return occupied; }
    public boolean isOccupied() { return occupied != null; }
}
